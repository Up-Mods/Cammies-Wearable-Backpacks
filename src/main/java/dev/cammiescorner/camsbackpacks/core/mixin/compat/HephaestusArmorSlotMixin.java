package dev.cammiescorner.camsbackpacks.core.mixin.compat;

import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.tables.menu.slot.ArmorSlot;

@Mixin(ArmorSlot.class)
public class HephaestusArmorSlotMixin extends Slot {

    private HephaestusArmorSlotMixin(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
        throw new UnsupportedOperationException();
    }

    @Inject(method = "mayPickup", at = @At("HEAD"), cancellable = true)
    private void camsbackpacks$mayPickup(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (this.getItem().getItem() instanceof BackpackItem) {
            cir.setReturnValue(false);
        }
    }

}
