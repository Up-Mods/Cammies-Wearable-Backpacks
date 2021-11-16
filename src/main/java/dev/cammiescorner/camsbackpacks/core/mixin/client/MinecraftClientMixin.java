package dev.cammiescorner.camsbackpacks.core.mixin.client;

import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import dev.cammiescorner.camsbackpacks.common.network.OpenBackpackScreenPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
	@Shadow @Nullable public ClientPlayerEntity player;

	@Inject(method = "handleInputEvents", at = @At(value = "INVOKE",
			target = "net/minecraft/client/MinecraftClient.setScreen(Lnet/minecraft/client/gui/screen/Screen;)V",
			ordinal = 1
	), cancellable = true)
	public void setScreen(CallbackInfo info) {
		if(player != null && !player.isCreative() && player.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof BackpackItem) {
			OpenBackpackScreenPacket.send();
			info.cancel();
		}
	}
}
