package dev.cammiescorner.camsbackpacks.quilt.mixin.compat;

import dev.cammiescorner.camsbackpacks.item.BackpackItem;
import me.lizardofoz.inventorio.slot.ArmorSlot;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorSlot.class)
public class InventorioArmorSlotMixin extends Slot {

    private InventorioArmorSlotMixin(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
        throw new UnsupportedOperationException();
    }

    @Inject(method = "mayPickup", at = @At("HEAD"), cancellable = true)
    private void camsbackpacks$mayPickup(CallbackInfoReturnable<Boolean> cir) {
        if (this.getItem().getItem() instanceof BackpackItem) {
            cir.setReturnValue(false);
        }
    }
}
