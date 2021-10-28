package dev.cammiescorner.camsbackpacks.client.renderers;

import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.client.CamsBackpacksClient;
import dev.cammiescorner.camsbackpacks.client.models.BackpackModel;
import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class BackpackRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
	private final BackpackModel<T> backpack;
	private final Identifier texturePath = new Identifier(CamsBackpacks.MOD_ID, "textures/block/backpack.png");

	public BackpackRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader) {
		super(context);
		backpack = new BackpackModel<>(loader.getModelPart(CamsBackpacksClient.BACKPACK));
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		if(entity instanceof PlayerEntity) {
			ItemStack stack = entity.getEquippedStack(EquipmentSlot.CHEST);

			if(stack.getItem() instanceof BackpackItem item) {
				float[] colour = item.getColour().getColorComponents();
				float r = colour[0];
				float g = colour[1];
				float b = colour[2];

				matrices.push();
				matrices.translate(0.0D, 0.0D, 0.025D);
				this.getContextModel().copyStateTo(this.backpack);
				this.backpack.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
				VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getEntityTranslucent(texturePath), false, stack.hasGlint());
				this.backpack.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, r, g, b, 1.0F);
				matrices.pop();
			}
		}
	}
}
