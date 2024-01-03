package dev.cammiescorner.camsbackpacks.core.mixin.client;

import dev.cammiescorner.camsbackpacks.client.CamsBackpacksClient;
import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import dev.cammiescorner.camsbackpacks.common.network.OpenBackpackScreenPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    @Shadow
    @Nullable
    public LocalPlayer player;

    @Shadow
    @Nullable
    public ClientLevel level;

    @Inject(method = "handleKeybinds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;setScreen(Lnet/minecraft/client/gui/screens/Screen;)V", ordinal = 1), cancellable = true)
    private void camsbackpacks$setScreen(CallbackInfo info) {
        if (this.level != null && this.player != null && !this.player.isCreative() && CamsBackpacksClient.backpackScreenIsOpen && this.player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof BackpackItem) {
            if(!CamsBackpacksClient.chestSlotUiEnabled) {
                CamsBackpacksClient.backpackScreenIsOpen = false;
                return;
            }
            OpenBackpackScreenPacket.send();
            info.cancel();
        }
    }
}
