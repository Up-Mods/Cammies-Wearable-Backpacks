package dev.cammiescorner.camsbackpacks.common.blocks.entities;

import dev.cammiescorner.camsbackpacks.common.screen.BackpackScreenHandler;
import dev.cammiescorner.camsbackpacks.core.registry.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

public class BackpackBlockEntity extends BlockEntity implements Inventory, NamedScreenHandlerFactory
{
	public final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(54, ItemStack.EMPTY);
	public boolean wasPickedUp = false;

	public BackpackBlockEntity(BlockEntityType<?> type)
	{
		super(type);
	}

	public BackpackBlockEntity()
	{
		this(ModBlockEntities.BACKPACK);
	}

	@Override
	public void fromTag(BlockState state, CompoundTag tag)
	{
		super.fromTag(state, tag);
		Inventories.fromTag(tag, inventory);
		wasPickedUp = tag.getBoolean("PickedUp");
	}

	@Override
	public CompoundTag toTag(CompoundTag tag)
	{
		Inventories.toTag(tag, inventory);
		tag.putBoolean("PickedUp", wasPickedUp);
		return super.toTag(tag);
	}

	@Override
	public int size()
	{
		return inventory.size();
	}

	@Override
	public boolean isEmpty()
	{
		return inventory.isEmpty();
	}

	@Override
	public ItemStack getStack(int slot)
	{
		return inventory.get(slot);
	}

	@Override
	public ItemStack removeStack(int slot, int amount)
	{
		return Inventories.splitStack(inventory, slot, amount);
	}

	@Override
	public ItemStack removeStack(int slot)
	{
		return Inventories.removeStack(inventory, slot);
	}

	@Override
	public void setStack(int slot, ItemStack stack)
	{
		inventory.set(slot, stack);
	}

	@Override
	public boolean canPlayerUse(PlayerEntity player)
	{
		return player.squaredDistanceTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) < 16;
	}

	@Override
	public void clear()
	{
		inventory.clear();
	}

	@Override
	public Text getDisplayName()
	{
		return new TranslatableText(getCachedState().getBlock().getTranslationKey());
	}

	@Override
	public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player)
	{
		return new BackpackScreenHandler(syncId, inv, this);
	}
}
