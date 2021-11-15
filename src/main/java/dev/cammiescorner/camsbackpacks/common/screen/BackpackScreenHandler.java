package dev.cammiescorner.camsbackpacks.common.screen;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.core.mixin.CraftingScreenHandlerAccessor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;

public class BackpackScreenHandler extends ScreenHandler {
	private final PlayerEntity player;
	private final Inventory inventory;
	private final CraftingInventory input;
	private final CraftingResultInventory result;
	private final ScreenHandlerContext context;
	public final boolean isBlockEntity;

	public BackpackScreenHandler(int syncId, PlayerInventory playerInventory, boolean isBlockEntity) {
		this(syncId, playerInventory, new SimpleInventory(36), ScreenHandlerContext.EMPTY, isBlockEntity);
	}

	public BackpackScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, ScreenHandlerContext context, boolean isBlockEntity) {
		super(CamsBackpacks.BACKPACK_SCREEN_HANDLER, syncId);
		checkSize(inventory, 36);
		this.player = playerInventory.player;
		this.inventory = inventory;
		this.input = new CraftingInventory(this, 3, 3);
		this.result = new CraftingResultInventory();
		this.context = context;
		this.isBlockEntity = isBlockEntity;
		inventory.onOpen(player);

		addSlot(new CraftingResultSlot(player, input, result, 0, 273, 70 + 3 * 18));

		int y;
		int x;

		// Crafting table inventory
		for(y = 0; y < 3; ++y)
			for(x = 0; x < 3; ++ x)
				addSlot(new Slot(this.input, x + y * 3, 255 + x * 18, 50 + y * 18));

		// Backpack inventory
		for(y = 0; y < 4; ++y)
			for(x = 0; x < 9; ++x)
				addSlot(new Slot(inventory, x + y * 9, 81 + x * 18, 18 + y * 18));

		// Player inventory
		for(y = 0; y < 3; ++y)
			for(x = 0; x < 9; ++x)
				addSlot(new Slot(playerInventory, x + y * 9 + 9, 81 + x * 18, 108 + y * 18));

		// Player hotbar
		for(y = 0; y < 9; ++y)
			addSlot(new Slot(playerInventory, y, 81 + y * 18, 166));
	}

	@Override
	public void onContentChanged(Inventory inventory) {
		context.run((world, pos) -> CraftingScreenHandlerAccessor.callUpdateResult(this, world, player, input, result));
	}

	@Override
	public void close(PlayerEntity player) {
		super.close(player);
		context.run((world, pos) -> dropInventory(player, input));
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return inventory.canPlayerUse(player);
	}

	@Override
	public ItemStack transferSlot(PlayerEntity player, int index) {
		ItemStack newStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);

		if(slot.hasStack()) {
			ItemStack originalStack = slot.getStack();
			newStack = originalStack.copy();

			if(index < inventory.size()) {
				if(!insertItem(originalStack, inventory.size(), slots.size(), true))
					return ItemStack.EMPTY;
			}
			else if(!insertItem(originalStack, 0, inventory.size(), false))
				return ItemStack.EMPTY;

			if(originalStack.isEmpty())
				slot.setStack(ItemStack.EMPTY);
			else
				slot.markDirty();

			if(originalStack.getCount() == newStack.getCount())
				return ItemStack.EMPTY;

			slot.onTakeItem(player, originalStack);
		}

		return newStack;
	}
}
