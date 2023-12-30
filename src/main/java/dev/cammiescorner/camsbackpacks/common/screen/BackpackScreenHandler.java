package dev.cammiescorner.camsbackpacks.common.screen;

import com.mojang.datafixers.util.Pair;
import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import dev.cammiescorner.camsbackpacks.core.mixin.accessor.CraftingMenuAccessor;
import dev.cammiescorner.camsbackpacks.core.mixin.accessor.InventoryMenuAccessor;
import dev.cammiescorner.camsbackpacks.core.registry.ModScreenHandlers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class BackpackScreenHandler extends AbstractContainerMenu {
    private final Player player;
    private final Container inventory;
    private final TransientCraftingContainer input;
    private final ResultContainer result;
    private final ContainerLevelAccess context;
    public final boolean isBlockEntity;
    public BlockPos blockPos;

    public BackpackScreenHandler(int syncId, Inventory playerInventory, BlockPos blockPos, boolean isBlockEntity) {
        this(syncId, playerInventory, new SimpleContainer(36), ContainerLevelAccess.NULL, blockPos, isBlockEntity);
    }

    public BackpackScreenHandler(int syncId, Inventory playerInventory, Container inventory, ContainerLevelAccess context, BlockPos blockPos, boolean isBlockEntity) {
        super(ModScreenHandlers.BACKPACK_SCREEN_HANDLER, syncId);
        checkContainerSize(inventory, 36);
        this.player = playerInventory.player;
        this.inventory = inventory;
        this.input = new TransientCraftingContainer(this, 3, 3);
        this.result = new ResultContainer();
        this.context = context;
        this.blockPos = blockPos;
        this.isBlockEntity = isBlockEntity;
        inventory.startOpen(player);

        int y;
        int x;

        // Backpack inventory
        for (y = 0; y < 4; ++y)
            for (x = 0; x < 9; ++x)
                addSlot(new Slot(inventory, x + y * 9, 81 + x * 18, 18 + y * 18));

        // Player inventory
        for (y = 0; y < 3; ++y)
            for (x = 0; x < 9; ++x)
                addSlot(new Slot(playerInventory, x + y * 9 + 9, 81 + x * 18, 108 + y * 18));

        // Player hotbar
        for (y = 0; y < 9; ++y)
            addSlot(new Slot(playerInventory, y, 81 + y * 18, 166));

        final EquipmentSlot[] equipmentSlots = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

        // Player armour
        for (y = 0; y < 4; ++y) {
            final EquipmentSlot equipmentSlot = equipmentSlots[y];

            addSlot(new Slot(playerInventory, 39 - y, 8, 51 + y * 18) {
                @Override
                public int getMaxStackSize() {
                    return 1;
                }

                @Override
                public boolean mayPlace(ItemStack stack) {
                    return equipmentSlot == Mob.getEquipmentSlotForItem(stack);
                }

                @Override
                public boolean mayPickup(Player playerEntity) {
                    ItemStack stack = getItem();
                    return (stack.isEmpty() || playerEntity.isCreative() || !EnchantmentHelper.hasBindingCurse(stack)) && !(stack.getItem() instanceof BackpackItem) && super.mayPickup(playerEntity);
                }

                @Override
                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenuAccessor.camsbackpacks$getEmptySlotsTextures()[equipmentSlot.getIndex()]);
                }
            });
        }

        // Player offhand
        this.addSlot(new Slot(playerInventory, 40, 8, 123) {
            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
            }
        });

        // Crafting table inventory
        for (y = 0; y < 3; ++y)
            for (x = 0; x < 3; ++x)
                addSlot(new Slot(input, x + y * 3, 255 + x * 18, 46 + y * 18));

        addSlot(new ResultSlot(player, input, result, 0, 273, 71 + 3 * 18));
    }

    @Override
    public void slotsChanged(Container inventory) {
        context.execute((world, pos) -> CraftingMenuAccessor.camsbackpacks$callSlotChangedCraftingGrid(this, world, player, input, result));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack oldStack = slot.getItem();
            newStack = oldStack.copy();
            EquipmentSlot equipmentSlot = Mob.getEquipmentSlotForItem(newStack);
            int armourSlotId = 75 - equipmentSlot.getIndex();

            if (index < inventory.getContainerSize()) {
                if (!moveItemStackTo(oldStack, inventory.getContainerSize(), slots.size() - 15, true))
                    return ItemStack.EMPTY;
            } else if (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR && !slots.get(armourSlotId).hasItem()) {
                if (!this.moveItemStackTo(oldStack, armourSlotId, armourSlotId + 1, false))
                    return ItemStack.EMPTY;
            } else if (equipmentSlot == EquipmentSlot.OFFHAND && !slots.get(76).hasItem()) {
                if (!this.moveItemStackTo(oldStack, 76, 77, false))
                    return ItemStack.EMPTY;
            } else if (index == 0) {
                if (!moveItemStackTo(oldStack, 0, inventory.getContainerSize() + player.getInventory().getContainerSize(), false))
                    return ItemStack.EMPTY;
            } else if (!moveItemStackTo(oldStack, 0, inventory.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }


            if (oldStack.isEmpty())
                slot.setByPlayer(ItemStack.EMPTY);
            else
                slot.setChanged();

            if (oldStack.getCount() == newStack.getCount())
                return ItemStack.EMPTY;

            slot.onTake(player, oldStack);
        }

        return newStack;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        context.execute((world, pos) -> {
            if (!world.isClientSide() && !isBlockEntity) {
                ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);
                CompoundTag tag = stack.getOrCreateTag();
                NonNullList<ItemStack> inv = NonNullList.withSize(36, ItemStack.EMPTY);

                for (int i = 0; i < inventory.getContainerSize(); i++)
                    inv.set(i, inventory.getItem(i));

                ContainerHelper.saveAllItems(tag, inv);
            }

            clearContainer(player, input);
        });
    }

    @Override
    public boolean stillValid(Player player) {
        return inventory.stillValid(player);
    }
}
