package dev.cammiescorner.camsbackpacks.core.mixin;

import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/screen/PlayerScreenHandler$1")
public abstract class ArmourSlotMixin extends Slot {
	public ArmourSlotMixin(Inventory inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}

	@Inject(method = "canTakeItems(Lnet/minecraft/entity/player/PlayerEntity;)Z", at = @At("HEAD"), cancellable = true)
	public void canTakeItems(PlayerEntity playerEntity, CallbackInfoReturnable<Boolean> info) {
		if(getStack().getItem() instanceof BackpackItem)
			info.setReturnValue(false);
	}
}
