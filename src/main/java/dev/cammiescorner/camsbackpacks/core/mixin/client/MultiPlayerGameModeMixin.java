package dev.cammiescorner.camsbackpacks.core.mixin.client;

import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import dev.cammiescorner.camsbackpacks.common.network.PlaceBackpackPacket;
import dev.cammiescorner.camsbackpacks.core.BackpacksConfig;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public abstract class MultiPlayerGameModeMixin {

    @Inject(method = "useItemOn", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;startPrediction(Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/client/multiplayer/prediction/PredictiveAction;)V"), cancellable = true)
    private void camsbackpacks$interactBlock(LocalPlayer player, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> info) {
        if (BackpacksConfig.sneakPlaceBackpack && player.isShiftKeyDown() && player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof BackpackItem && player.getOffhandItem().isEmpty() && player.getMainHandItem().isEmpty()) {
            PlaceBackpackPacket.send(hitResult);
            info.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}
