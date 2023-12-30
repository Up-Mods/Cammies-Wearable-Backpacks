package dev.cammiescorner.camsbackpacks.client.renderers;

import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.camsbackpacks.CamsBackpacks;
import dev.cammiescorner.camsbackpacks.client.CamsBackpacksClient;
import dev.cammiescorner.camsbackpacks.client.models.BackpackModel;
import dev.cammiescorner.camsbackpacks.common.items.BackpackItem;
import dev.cammiescorner.camsbackpacks.core.registry.ModBlocks;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
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

	public BackpackRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader) {
		super(context);
		backpack = new BackpackModel<>(loader.getModelPart(CamsBackpacksClient.BACKPACK));
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		if(entity instanceof PlayerEntity) {
			Identifier texturePath = CamsBackpacks.id("textures/block/backpack.png");
			ItemStack stack = entity.getEquippedStack(EquipmentSlot.CHEST);

			if(stack.getItem() instanceof BackpackItem item) {
				float[] colour = item.getColour().getColorComponents();
				float r = colour[0];
				float g = colour[1];
				float b = colour[2];

				if(stack.isOf(ModBlocks.GAY_BACKPACK.asItem()))
					texturePath = CamsBackpacks.id("textures/block/gay_pride_backpack.png");
				if(stack.isOf(ModBlocks.LESBIAN_BACKPACK.asItem()))
					texturePath = CamsBackpacks.id("textures/block/lesbian_pride_backpack.png");
				if(stack.isOf(ModBlocks.QPOC_BACKPACK.asItem()))
					texturePath = CamsBackpacks.id("textures/block/qpoc_pride_backpack.png");
				if(stack.isOf(ModBlocks.BI_BACKPACK.asItem()))
					texturePath = CamsBackpacks.id("textures/block/bi_pride_backpack.png");
				if(stack.isOf(ModBlocks.PAN_BACKPACK.asItem()))
					texturePath = CamsBackpacks.id("textures/block/pan_pride_backpack.png");
				if(stack.isOf(ModBlocks.TRANS_BACKPACK.asItem()))
					texturePath = CamsBackpacks.id("textures/block/trans_pride_backpack.png");
				if(stack.isOf(ModBlocks.GENDERQUEER_BACKPACK.asItem()))
					texturePath = CamsBackpacks.id("textures/block/genderqueer_pride_backpack.png");
				if(stack.isOf(ModBlocks.GENDERFLUID_BACKPACK.asItem()))
					texturePath = CamsBackpacks.id("textures/block/genderfluid_pride_backpack.png");
				if(stack.isOf(ModBlocks.ENBY_BACKPACK.asItem()))
					texturePath = CamsBackpacks.id("textures/block/enby_pride_backpack.png");
				if(stack.isOf(ModBlocks.ACE_BACKPACK.asItem()))
					texturePath = CamsBackpacks.id("textures/block/ace_pride_backpack.png");
				if(stack.isOf(ModBlocks.DEMI_BACKPACK.asItem()))
					texturePath = CamsBackpacks.id("textures/block/demi_pride_backpack.png");
				if(stack.isOf(ModBlocks.ARO_BACKPACK.asItem()))
					texturePath = CamsBackpacks.id("textures/block/aro_pride_backpack.png");
				if(stack.isOf(ModBlocks.AGENDER_BACKPACK.asItem()))
					texturePath = CamsBackpacks.id("textures/block/agender_pride_backpack.png");

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
