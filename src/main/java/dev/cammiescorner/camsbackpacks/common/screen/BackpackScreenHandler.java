package dev.cammiescorner.camsbackpacks.common.screen;

import com.mojang.datafixers.util.Pair;
import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import dev.cammiescorner.camsbackpacks.core.mixin.CraftingScreenHandlerAccessor;
import dev.cammiescorner.camsbackpacks.core.mixin.PlayerScreenHandlerAccessor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;

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

		int y;
		int x;

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

		final EquipmentSlot[] equipmentSlots = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

		// Player armour
		for(y = 0; y < 4; ++y) {
			final EquipmentSlot equipmentSlot = equipmentSlots[y];

			addSlot(new Slot(playerInventory, 39 - y, 8, 51 + y * 18) {
				@Override
				public int getMaxItemCount() {
					return 1;
				}

				@Override
				public boolean canInsert(ItemStack stack) {
					return equipmentSlot == MobEntity.getPreferredEquipmentSlot(stack);
				}

				@Override
				public boolean canTakeItems(PlayerEntity playerEntity) {
					ItemStack stack = getStack();
					return (stack.isEmpty() || playerEntity.isCreative() || !EnchantmentHelper.hasBindingCurse(stack) || stack.getItem() instanceof BackpackItem) && super.canTakeItems(playerEntity);
				}

				@Override
				public Pair<Identifier, Identifier> getBackgroundSprite() {
					return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, PlayerScreenHandlerAccessor.getEmptyArmorSlotTex()[equipmentSlot.getEntitySlotId()]);
				}
			});
		}

		// Player offhand
		this.addSlot(new Slot(playerInventory, 40, 8, 123) {
			@Override
			public Pair<Identifier, Identifier> getBackgroundSprite() {
				return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, PlayerScreenHandler.EMPTY_OFFHAND_ARMOR_SLOT);
			}
		});

		// Crafting table inventory
		for(y = 0; y < 3; ++y)
			for(x = 0; x < 3; ++x)
				addSlot(new Slot(input, x + y * 3, 255 + x * 18, 50 + y * 18));

		addSlot(new CraftingResultSlot(player, input, result, 0, 273, 70 + 3 * 18));
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
			ItemStack oldStack = slot.getStack();
			newStack = oldStack.copy();
			EquipmentSlot equipmentSlot = MobEntity.getPreferredEquipmentSlot(newStack);
			int armourSlotId = 75 - equipmentSlot.getEntitySlotId();

			if(index < inventory.size()) {
				if(!insertItem(oldStack, inventory.size(), slots.size() - 15, true))
					return ItemStack.EMPTY;
			}
			else if(equipmentSlot.getType() == EquipmentSlot.Type.ARMOR && !slots.get(armourSlotId).hasStack()) {
				if(!this.insertItem(oldStack, armourSlotId, armourSlotId + 1, false))
					return ItemStack.EMPTY;
			}
			else if (equipmentSlot == EquipmentSlot.OFFHAND && !slots.get(76).hasStack()) {
				if(!this.insertItem(oldStack, 76, 77, false))
					return ItemStack.EMPTY;
			}
			else if(!insertItem(oldStack, 0, inventory.size(), false))
				return ItemStack.EMPTY;

			if(oldStack.isEmpty())
				slot.setStack(ItemStack.EMPTY);
			else
				slot.markDirty();

			if(oldStack.getCount() == newStack.getCount())
				return ItemStack.EMPTY;

			slot.onTakeItem(player, oldStack);
		}

		return newStack;
	}
}