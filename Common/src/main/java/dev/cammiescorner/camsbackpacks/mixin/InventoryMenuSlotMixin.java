package dev.cammiescorner.camsbackpacks.mixin;

import dev.cammiescorner.camsbackpacks.item.BackpackItem;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/world/inventory/InventoryMenu$1")
public abstract class InventoryMenuSlotMixin extends Slot {

    private InventoryMenuSlotMixin(Container inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        throw new UnsupportedOperationException();
    }

    @Inject(method = "mayPickup(Lnet/minecraft/world/entity/player/Player;)Z", at = @At("HEAD"), cancellable = true)
    private void camsbackpacks$mayPickup(Player playerEntity, CallbackInfoReturnable<Boolean> info) {
        if (getItem().getItem() instanceof BackpackItem)
            info.setReturnValue(false);
    }
}
