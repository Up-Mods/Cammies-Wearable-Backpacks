package dev.cammiescorner.camsbackpacks.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.client.CamsBackpacksClient;
import dev.cammiescorner.camsbackpacks.client.models.BackpackModel;
import dev.cammiescorner.camsbackpacks.init.ModBlocks;
import dev.cammiescorner.camsbackpacks.item.BackpackItem;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class BackpackRenderer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final BackpackModel<T> backpack;

    public BackpackRenderer(RenderLayerParent<T, M> context, EntityModelSet loader) {
        super(context);
        backpack = new BackpackModel<>(loader.bakeLayer(CamsBackpacksClient.BACKPACK));
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (entity instanceof Player) {
            ResourceLocation texturePath = CamsBackpacks.id("textures/block/backpack.png");
            ItemStack stack = entity.getItemBySlot(EquipmentSlot.CHEST);

            if (stack.getItem() instanceof BackpackItem item) {
                float[] colour = item.getColour().getTextureDiffuseColors();
                float r = colour[0];
                float g = colour[1];
                float b = colour[2];

                if (stack.is(ModBlocks.GAY_BACKPACK.get().asItem()))
                    texturePath = CamsBackpacks.id("textures/block/gay_pride_backpack.png");
                if (stack.is(ModBlocks.LESBIAN_BACKPACK.get().asItem()))
                    texturePath = CamsBackpacks.id("textures/block/lesbian_pride_backpack.png");
                if (stack.is(ModBlocks.QPOC_BACKPACK.get().asItem()))
                    texturePath = CamsBackpacks.id("textures/block/qpoc_pride_backpack.png");
                if (stack.is(ModBlocks.BI_BACKPACK.get().asItem()))
                    texturePath = CamsBackpacks.id("textures/block/bi_pride_backpack.png");
                if (stack.is(ModBlocks.PAN_BACKPACK.get().asItem()))
                    texturePath = CamsBackpacks.id("textures/block/pan_pride_backpack.png");
                if (stack.is(ModBlocks.TRANS_BACKPACK.get().asItem()))
                    texturePath = CamsBackpacks.id("textures/block/trans_pride_backpack.png");
                if (stack.is(ModBlocks.GENDERQUEER_BACKPACK.get().asItem()))
                    texturePath = CamsBackpacks.id("textures/block/genderqueer_pride_backpack.png");
                if (stack.is(ModBlocks.GENDERFLUID_BACKPACK.get().asItem()))
                    texturePath = CamsBackpacks.id("textures/block/genderfluid_pride_backpack.png");
                if (stack.is(ModBlocks.ENBY_BACKPACK.get().asItem()))
                    texturePath = CamsBackpacks.id("textures/block/enby_pride_backpack.png");
                if (stack.is(ModBlocks.ACE_BACKPACK.get().asItem()))
                    texturePath = CamsBackpacks.id("textures/block/ace_pride_backpack.png");
                if (stack.is(ModBlocks.DEMI_BACKPACK.get().asItem()))
                    texturePath = CamsBackpacks.id("textures/block/demi_pride_backpack.png");
                if (stack.is(ModBlocks.ARO_BACKPACK.get().asItem()))
                    texturePath = CamsBackpacks.id("textures/block/aro_pride_backpack.png");
                if (stack.is(ModBlocks.AGENDER_BACKPACK.get().asItem()))
                    texturePath = CamsBackpacks.id("textures/block/agender_pride_backpack.png");

                matrices.pushPose();
                matrices.translate(0.0D, 0.0D, 0.025D);
                this.getParentModel().copyPropertiesTo(this.backpack);
                this.backpack.setupAnim(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
                VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumers, RenderType.entityTranslucent(texturePath), false, stack.hasFoil());
                this.backpack.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, r, g, b, 1.0F);
                matrices.popPose();
            }
        }
    }
}
