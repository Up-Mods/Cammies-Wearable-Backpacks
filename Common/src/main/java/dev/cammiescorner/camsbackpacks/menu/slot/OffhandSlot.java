package dev.cammiescorner.camsbackpacks.menu.slot;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class OffhandSlot extends Slot {

    private final Player player;

    public OffhandSlot(Inventory playerInventory, int slotIndex, int x, int y, Player player) {
        super(playerInventory, slotIndex, x, y);
        this.player = player;
    }

    @Override
    public void setByPlayer(ItemStack stack) {
        InventoryMenu.onEquipItem(this.player, EquipmentSlot.OFFHAND, stack, this.getItem());
        super.setByPlayer(stack);
    }

    @Override
    public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
        return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
    }
}
