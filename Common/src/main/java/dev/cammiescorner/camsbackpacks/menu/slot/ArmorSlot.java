package dev.cammiescorner.camsbackpacks.menu.slot;

import com.mojang.datafixers.util.Pair;
import dev.cammiescorner.camsbackpacks.item.BackpackItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class ArmorSlot extends Slot {

    private final EquipmentSlot equipmentSlot;
    private final Player player;

    public ArmorSlot(Inventory playerInventory, int slotIndex, int x, int y, EquipmentSlot equipmentSlot, Player player) {
        super(playerInventory, slotIndex, x, y);
        this.equipmentSlot = equipmentSlot;
        this.player = player;
    }

    @Override
    public void setByPlayer(ItemStack stack) {
        InventoryMenu.onEquipItem(this.player, this.equipmentSlot, stack, this.getItem());
        super.setByPlayer(stack);
    }

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
        return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.TEXTURE_EMPTY_SLOTS[equipmentSlot.getIndex()]);
    }
}
