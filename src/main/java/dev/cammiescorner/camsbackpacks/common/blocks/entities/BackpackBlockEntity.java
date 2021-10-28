package dev.cammiescorner.camsbackpacks.common.blocks.entities;

import dev.cammiescorner.camsbackpacks.core.registry.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class BackpackBlockEntity extends BlockEntity implements Inventory, NamedScreenHandlerFactory {
	public final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(54, ItemStack.EMPTY);
	public boolean wasPickedUp = false;

	public BackpackBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.BACKPACK, pos, state);
	}

	@Override
	public void readNbt(NbtCompound tag) {
		super.readNbt(tag);
		Inventories.readNbt(tag, inventory);
		wasPickedUp = tag.getBoolean("PickedUp");
	}

	@Override
	public NbtCompound writeNbt(NbtCompound tag) {
		Inventories.writeNbt(tag, inventory);
		tag.putBoolean("PickedUp", wasPickedUp);
		return super.writeNbt(tag);
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
		return player.squaredDistanceTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) < 16;
	}

	@Override
	public void clear() {
		inventory.clear();
	}

	@Override
	public Text getDisplayName() {
		return new TranslatableText(getCachedState().getBlock().getTranslationKey());
	}

	@Override
	public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
		return GenericContainerScreenHandler.createGeneric9x3(syncId, inv, this);
	}
}
