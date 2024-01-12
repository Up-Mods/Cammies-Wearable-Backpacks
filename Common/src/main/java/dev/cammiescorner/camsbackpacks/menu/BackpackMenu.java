package dev.cammiescorner.camsbackpacks.menu;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.init.ModScreenHandlers;
import dev.cammiescorner.camsbackpacks.menu.slot.ArmorSlot;
import dev.cammiescorner.camsbackpacks.menu.slot.OffhandSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BackpackMenu extends AbstractContainerMenu {

    private static final List<EquipmentSlot> ARMOR_SLOTS = List.of(EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD);

    private final Player player;
    private final Container inventory;
    private final TransientCraftingContainer craftingInv;
    private final ResultContainer craftingResultInv;
    private final ContainerLevelAccess levelAccess;
    public final boolean isBlockEntity;
    public BlockPos blockPos;

    public BackpackMenu(int syncId, Inventory playerInventory, BlockPos blockPos, boolean isBlockEntity) {
        this(syncId, playerInventory, new SimpleContainer(36), ContainerLevelAccess.NULL, blockPos, isBlockEntity);
    }

    public BackpackMenu(int syncId, Inventory playerInventory, Container inventory, ContainerLevelAccess levelAccess, BlockPos blockPos, boolean isBlockEntity) {
        super(ModScreenHandlers.BACKPACK_SCREEN_HANDLER.get(), syncId);
        checkContainerSize(inventory, 36);
        this.player = playerInventory.player;
        this.inventory = inventory;
        this.craftingInv = new TransientCraftingContainer(this, 3, 3);
        this.craftingResultInv = new ResultContainer();
        this.levelAccess = levelAccess;
        this.blockPos = blockPos;
        this.isBlockEntity = isBlockEntity;
        inventory.startOpen(player);

        // Crafting result (slot 0) (global: 0)
        addSlot(new ResultSlot(player, craftingInv, craftingResultInv, 0, 273, 71 + 3 * 18));

        // Crafting table inventory (slots 1-9) (global: 1-9)
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                addSlot(new Slot(craftingInv, x + y * 3, 255 + x * 18, 46 + y * 18));
            }
        }

        // Backpack inventory  (slots 0-35) (global: 10-45)
        for (int y = 0; y < 4; ++y) {
            for (int x = 0; x < 9; ++x) {
                addSlot(new Slot(inventory, x + y * 9, 81 + x * 18, 18 + y * 18));
            }
        }

        // Player inventory (slots 9-35) (global: 46-72)
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                addSlot(new Slot(playerInventory, x + y * 9 + 9, 81 + x * 18, 108 + y * 18));
            }
        }

        // Player hotbar (slots 0-8) (global: 73-82)
        for (int i = 0; i < 9; ++i) {
            addSlot(new Slot(playerInventory, i, 81 + i * 18, 166));
        }

        // Player armor (slot 36-39) (global: 82-85)
        for (int i = 0; i < ARMOR_SLOTS.size(); ++i) {
            EquipmentSlot equipmentSlot = ARMOR_SLOTS.get(i);
            addSlot(new ArmorSlot(playerInventory, equipmentSlot.getIndex(36), 8, 105 - i * 18, equipmentSlot, player));
        }

        // Player offhand (slot 40) (global: 86)
        this.addSlot(new OffhandSlot(playerInventory, 40, 8, 123, player));

    }

    @Override
    public void slotsChanged(Container inventory) {
        levelAccess.execute((world, pos) -> CraftingMenu.slotChangedCraftingGrid(this, world, player, craftingInv, craftingResultInv));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack oldStack = slot.getItem();
            newStack = oldStack.copy();

            if (index == 0) { // moving out of result slot (0) -> inv first, then backpack, then hotbar
                this.levelAccess.execute((world, pos) -> oldStack.getItem().onCraftedBy(oldStack, world, player));

                // try player inv
                if (!moveItemStackTo(oldStack, 46, 73, false)) {

                    // try backpack inv
                    if (!moveItemStackTo(oldStack, 10, 46, false)) {

                        // try hotbar
                        if (!moveItemStackTo(oldStack, 73, 82, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }
                slot.onQuickCraft(oldStack, newStack);
            } else if (index <= 9) { // moving out of crafting inv (1-9) -> inv first, then backpack, then hotbar

                // try player inv
                if (!moveItemStackTo(oldStack, 46, 73, false)) {

                    // try backpack inv
                    if (!moveItemStackTo(oldStack, 10, 46, false)) {

                        // try hotbar
                        if (!moveItemStackTo(oldStack, 73, 82, false)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }

            } else if (index <= 45) { // moving out of backpack inv (10-45) -> armor inv, then hotbar, then regular inv

                // try preferred armor slot
                EquipmentSlot equipmentSlot = Mob.getEquipmentSlotForItem(newStack);
                int armorSlotId = equipmentSlot.getIndex(82);
                if (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR && !getSlot(armorSlotId).hasItem()) {
                    if (!moveItemStackTo(oldStack, armorSlotId, armorSlotId + 1, false)) {

                        // try hotbar
                        if (!moveItemStackTo(oldStack, 73, 82, false)) {

                            // try player inv
                            if (!moveItemStackTo(oldStack, 46, 73, true)) {
                                return ItemStack.EMPTY;
                            }
                        }
                    }
                } else {

                    // try hotbar
                    if (!moveItemStackTo(oldStack, 73, 82, false)) {

                        // try player inv
                        if (!moveItemStackTo(oldStack, 46, 73, true)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }

            } else if (index <= 72) { // moving out of player inv (46-72) -> backpack inv, then hotbar

                // try backpack inv
                if (!moveItemStackTo(oldStack, 10, 46, false)) {

                    // try hotbar
                    if (!moveItemStackTo(oldStack, 73, 82, true)) {
                        return ItemStack.EMPTY;
                    }
                }

            } else if (index <= 81) { // moving out of hotbar (73-81) -> backpack inv, then player inv

                // try backpack inv
                if (!moveItemStackTo(oldStack, 10, 46, false)) {

                    // try player inv
                    if (!moveItemStackTo(oldStack, 46, 73, false)) {
                        return ItemStack.EMPTY;
                    }
                }

            } else if (index <= 86) { // moving out of armor items (82-85) or offhand (86) -> backpack inv, then player inv, then hotbar

                // try backpack inv
                if (!moveItemStackTo(oldStack, 10, 46, false)) {

                    // try player inv
                    if (!moveItemStackTo(oldStack, 46, 73, false)) {

                        // try hotbar
                        if (!moveItemStackTo(oldStack, 73, 82, true)) {
                            return ItemStack.EMPTY;
                        }
                    }
                }

            } else {
                // crash the game so we get bug reports
                throw new IllegalArgumentException("[Cammies-Wearable-Backpacks] Tried to quick transfer out of Slot [" + index + "]; please report this to " + CamsBackpacks.getIssuesURL());
            }

            if (oldStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (oldStack.getCount() == newStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, oldStack);

            // can't put back into result slot, so drop remaining items
            if (index == 0) {
                player.drop(oldStack, false);
            }
        }

        return newStack;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        levelAccess.execute((world, pos) -> {
            if (!world.isClientSide() && !isBlockEntity) {
                ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);
                CompoundTag tag = stack.getOrCreateTag();
                NonNullList<ItemStack> inv = NonNullList.withSize(36, ItemStack.EMPTY);

                for (int i = 0; i < inventory.getContainerSize(); i++)
                    inv.set(i, inventory.getItem(i));

                ContainerHelper.saveAllItems(tag, inv);
            }

            clearContainer(player, craftingInv);
        });
    }

    @Override
    public boolean stillValid(Player player) {
        return inventory.stillValid(player);
    }

}
