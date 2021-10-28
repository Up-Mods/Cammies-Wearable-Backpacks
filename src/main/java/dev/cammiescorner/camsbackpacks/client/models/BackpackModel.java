// Made with Model Converter by Globox_Z
// Generate all required imports
package dev.cammiescorner.camsbackpacks.client.models;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.LivingEntity;

public class BackpackModel<T extends LivingEntity> extends AnimalModel<T> {
	private final ModelPart root;

	public BackpackModel(ModelPart root) {
		this.root = root.getChild("root");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData modelPartData1 = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData modelPartData2 = modelPartData1.addChild("straps", ModelPartBuilder.create().uv(0, 0).cuboid(-4.5F, -0.3F, -3.0F, 9.0F, 12.0F, 5.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		ModelPartData modelPartData3 = modelPartData2.addChild("backpack", ModelPartBuilder.create().uv(28, 0).cuboid(-4.5F, -3.0F, 2.0F, 9.0F, 16.0F, 6.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		modelPartData3.addChild("pocketRight", ModelPartBuilder.create().uv(0, 17).cuboid(3.5F, 7.0F, 3.0F, 3.0F, 5.0F, 4.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		modelPartData3.addChild("pocketLeft", ModelPartBuilder.create().uv(0, 17).cuboid(-6.5F, 7.0F, 3.0F, 3.0F, 5.0F, 4.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		modelPartData3.addChild("backPocket", ModelPartBuilder.create().uv(14, 20).cuboid(-3.0F, 1.0F, 8.0F, 6.0F, 8.0F, 2.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		return TexturedModelData.of(modelData, 64, 32);
	}

	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		if(entity.isInSneakingPose()) {
			root.pitch = 0.5F;
			root.pivotY = 3.2F;
		}
		else {
			root.pitch = 0.0F;
			root.pivotY = 0.0F;
		}
	}

	@Override
	protected Iterable<ModelPart> getHeadParts() {
		return ImmutableList.of();
	}

	@Override
	protected Iterable<ModelPart> getBodyParts() {
		return ImmutableList.of(root);
	}
}