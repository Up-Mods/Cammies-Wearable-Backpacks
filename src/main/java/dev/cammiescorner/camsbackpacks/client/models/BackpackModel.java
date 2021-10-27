package dev.cammiescorner.camsbackpacks.client.models;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.LivingEntity;

public class BackpackModel<T extends LivingEntity> extends AnimalModel<T> {
	private final ModelPart root;
	private final ModelPart straps;
	private final ModelPart backpack;
	private final ModelPart pocketRight;
	private final ModelPart pocketLeft;
	private final ModelPart backPocket;

	public BackpackModel() {
		textureWidth = 64;
		textureHeight = 32;
		root = new ModelPart(this);
		root.setPivot(0.0F, 0.0F, 0.0F);

		straps = new ModelPart(this);
		straps.setPivot(0.0F, 0.0F, 0.0F);
		root.addChild(straps);
		straps.setTextureOffset(0, 0).addCuboid(-4.5F, -0.3F, -3.0F, 9.0F, 12.0F, 5.0F, 0.0F, false);

		backpack = new ModelPart(this);
		backpack.setPivot(0.0F, 0.0F, 0.0F);
		straps.addChild(backpack);
		backpack.setTextureOffset(28, 0).addCuboid(-4.5F, -3.0F, 2.0F, 9.0F, 16.0F, 6.0F, 0.0F, false);

		pocketRight = new ModelPart(this);
		pocketRight.setPivot(0.0F, 0.0F, 0.0F);
		backpack.addChild(pocketRight);
		pocketRight.setTextureOffset(0, 17).addCuboid(3.5F, 7.0F, 3.0F, 3.0F, 5.0F, 4.0F, 0.0F, false);

		pocketLeft = new ModelPart(this);
		pocketLeft.setPivot(0.0F, 0.0F, 0.0F);
		backpack.addChild(pocketLeft);
		pocketLeft.setTextureOffset(0, 17).addCuboid(-6.5F, 7.0F, 3.0F, 3.0F, 5.0F, 4.0F, 0.0F, false);

		backPocket = new ModelPart(this);
		backPocket.setPivot(0.0F, 0.0F, 0.0F);
		backpack.addChild(backPocket);
		backPocket.setTextureOffset(14, 20).addCuboid(-3.0F, 1.0F, 8.0F, 6.0F, 8.0F, 2.0F, 0.0F, false);
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