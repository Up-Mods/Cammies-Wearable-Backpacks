package dev.cammiescorner.camsbackpacks.common.screen;

import com.mojang.datafixers.util.Pair;
import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import dev.cammiescorner.camsbackpacks.core.mixin.CraftingScreenHandlerAccessor;
import dev.cammiescorner.camsbackpacks.core.mixin.PlayerScreenHandlerAccessor;
import dev.cammiescorner.camsbackpacks.core.registry.ModScreenHandlers;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class BackpackScreenHandler extends ScreenHandler {
	private final PlayerEntity player;
	private final Inventory inventory;
	private final CraftingInventory input;
	private final CraftingResultInventory result;
	private final ScreenHandlerContext context;
	public final boolean isBlockEntity;
	public BlockPos blockPos;

	public BackpackScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos blockPos, boolean isBlockEntity) {
		this(syncId, playerInventory, new SimpleInventory(36), ScreenHandlerContext.EMPTY, blockPos, isBlockEntity);
	}

	public BackpackScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, ScreenHandlerContext context, BlockPos blockPos, boolean isBlockEntity) {
		super(ModScreenHandlers.BACKPACK_SCREEN_HANDLER, syncId);
		checkSize(inventory, 36);
		this.player = playerInventory.player;
		this.inventory = inventory;
		this.input = new CraftingInventory(this, 3, 3);
		this.result = new CraftingResultInventory();
		this.context = context;
		this.blockPos = blockPos;
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
					return (stack.isEmpty() || playerEntity.isCreative() || !EnchantmentHelper.hasBindingCurse(stack)) && !(stack.getItem() instanceof BackpackItem) && super.canTakeItems(playerEntity);
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
				addSlot(new Slot(input, x + y * 3, 255 + x * 18, 46 + y * 18));

		addSlot(new CraftingResultSlot(player, input, result, 0, 273, 71 + 3 * 18));
	}

	@Override
	public void onContentChanged(Inventory inventory) {
		context.run((world, pos) -> CraftingScreenHandlerAccessor.callUpdateResult(this, world, player, input, result));
	}

	@Override
	public ItemStack quickTransfer(PlayerEntity player, int index) {
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
			else if(index == 0) {
				if(!insertItem(oldStack, 0, inventory.size() + player.getInventory().size(), false))
					return ItemStack.EMPTY;
			}
			else if(!insertItem(oldStack, 0, inventory.size(), false)) {
				return ItemStack.EMPTY;
			}


			if(oldStack.isEmpty())
				slot.setStackByPlayer(ItemStack.EMPTY);
			else
				slot.markDirty();

			if(oldStack.getCount() == newStack.getCount())
				return ItemStack.EMPTY;

			slot.onTakeItem(player, oldStack);
		}

		return newStack;
	}

	@Override
	public void close(PlayerEntity player) {
		super.close(player);
		context.run((world, pos) -> {
			if(!world.isClient() && !isBlockEntity) {
				ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);
				NbtCompound tag = stack.getOrCreateNbt();
				DefaultedList<ItemStack> inv = DefaultedList.ofSize(36, ItemStack.EMPTY);

				for(int i = 0; i < inventory.size(); i++)
					inv.set(i, inventory.getStack(i));

				Inventories.writeNbt(tag, inv);
			}

			dropInventory(player, input);
		});
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return inventory.canPlayerUse(player);
	}
}
