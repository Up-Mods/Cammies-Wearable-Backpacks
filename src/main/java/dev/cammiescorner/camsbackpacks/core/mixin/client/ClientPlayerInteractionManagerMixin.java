package dev.cammiescorner.camsbackpacks.core.mixin.client;

import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import dev.cammiescorner.camsbackpacks.common.network.PlaceBackpackPacket;
import dev.cammiescorner.camsbackpacks.core.BackpacksConfig;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
	@Inject(method = "interactBlock", at = @At(value = "INVOKE",
			shift = At.Shift.AFTER,
			target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;sendSequencedPacket(Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/client/network/SequencedPacketCreator;)V"
	), cancellable = true)
	public void camsbackpacks$interactBlock(ClientPlayerEntity player, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> info) {
		if(BackpacksConfig.get().sneakPlaceBackpack && player.isSneaking() && player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof BackpackItem && player.getOffHandStack().isEmpty() && player.getMainHandStack().isEmpty()) {
			PlaceBackpackPacket.send(hitResult);
			info.setReturnValue(ActionResult.SUCCESS);
		}
	}
}
