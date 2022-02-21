package dev.cammiescorner.camsbackpacks.core.mixin.client;

import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import dev.cammiescorner.camsbackpacks.common.network.PlaceBackpackPacket;
import dev.cammiescorner.camsbackpacks.core.CamsConfig;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
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
			target = "net/minecraft/client/network/ClientPlayNetworkHandler.sendPacket(Lnet/minecraft/network/Packet;)V",
			ordinal = 2
	), cancellable = true)
	public void camsbackpacks$interactBlock(ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> info) {
		if(player.isSneaking() && player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof BackpackItem && player.getOffHandStack().isEmpty() && player.getMainHandStack().isEmpty() && CamsConfig.get().sneakPlaceBackpack) {
			PlaceBackpackPacket.send(hitResult);
			info.setReturnValue(ActionResult.SUCCESS);
		}
	}
}
