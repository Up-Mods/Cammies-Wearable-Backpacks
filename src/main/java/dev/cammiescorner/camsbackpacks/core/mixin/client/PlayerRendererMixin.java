package dev.cammiescorner.camsbackpacks.core.mixin.client;

import dev.cammiescorner.camsbackpacks.client.renderers.BackpackRenderer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    private PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> entityModel, float f) {
        super(context, entityModel, f);
        throw new UnsupportedOperationException();
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void camsbackpacks$init(EntityRendererProvider.Context context, boolean bl, CallbackInfo info) {
        this.addLayer(new BackpackRenderer<>(this, context.getModelSet()));
    }
}
