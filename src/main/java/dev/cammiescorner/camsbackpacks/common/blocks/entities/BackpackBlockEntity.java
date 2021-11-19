package dev.cammiescorner.camsbackpacks.common.blocks.entities;

import dev.cammiescorner.camsbackpacks.common.screen.BackpackScreenHandler;
import dev.cammiescorner.camsbackpacks.core.registry.ModBlockEntities;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class BackpackBlockEntity extends BlockEntity implements Inventory, Nameable, ExtendedScreenHandlerFactory {
	public final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(36, ItemStack.EMPTY);
	public boolean wasPickedUp = false;
	private Text name = Text.of("");

	public BackpackBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.BACKPACK, pos, state);
	}

	@Override
	public void readNbt(NbtCompound tag) {
		super.readNbt(tag);
		Inventories.readNbt(tag, inventory);
		wasPickedUp = tag.getBoolean("PickedUp");
		if(tag.contains("CustomName", NbtElement.STRING_TYPE))
			this.name = Text.Serializer.fromJson(tag.getString("CustomName"));
	}

	@Override
	public void writeNbt(NbtCompound tag) {
		Inventories.writeNbt(tag, inventory);
		tag.putBoolean("PickedUp", wasPickedUp);

		if(this.name != null)
			tag.putString("CustomName", Text.Serializer.toJson(this.name));

		super.writeNbt(tag);
	}

	@Override
	public int size() {
		return inventory.size();
	}

	@Override
	public boolean isEmpty() {
		return inventory.isEmpty();
	}

	@Override
	public ItemStack getStack(int slot) {
		return inventory.get(slot);
	}

	@Override
	public ItemStack removeStack(int slot, int amount) {
		return Inventories.splitStack(inventory, slot, amount);
	}

	@Override
	public ItemStack removeStack(int slot) {
		return Inventories.removeStack(inventory, slot);
	}

	@Override
	public void setStack(int slot, ItemStack stack) {
		inventory.set(slot, stack);
	}

	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		return !(player.squaredDistanceTo(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) > 64.0D);
	}

	@Override
	public void clear() {
		inventory.clear();
	}

	@Override
	public Text getName() {
		return name;
	}

	public void setName(Text text) {
		this.name = text;
		this.markDirty();
	}

	@Override
	public Text getDisplayName() {
		return getName();
	}

	@Override
	public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
		return new BackpackScreenHandler(syncId, inv, this, ScreenHandlerContext.create(player.world, getPos()), getPos(), true);
	}

	@Override
	public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
		buf.writeBlockPos(getPos());
		buf.writeBoolean(true);
	}
}
