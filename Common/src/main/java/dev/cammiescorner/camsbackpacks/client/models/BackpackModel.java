// Made with Model Converter by Globox_Z
// Generate all required imports
package dev.cammiescorner.camsbackpacks.client.models;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.LivingEntity;

public class BackpackModel<T extends LivingEntity> extends AgeableListModel<T> {
    private final ModelPart root;

    public BackpackModel(ModelPart root) {
        this.root = root.getChild("root");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition modelPartData1 = modelPartData.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition modelPartData2 = modelPartData1.addOrReplaceChild("straps", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -0.3F, -3.0F, 9.0F, 12.0F, 5.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition modelPartData3 = modelPartData2.addOrReplaceChild("backpack", CubeListBuilder.create().texOffs(28, 0).addBox(-4.5F, -3.0F, 2.0F, 9.0F, 16.0F, 6.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData3.addOrReplaceChild("pocketRight", CubeListBuilder.create().texOffs(0, 17).addBox(3.5F, 7.0F, 3.0F, 3.0F, 5.0F, 4.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData3.addOrReplaceChild("pocketLeft", CubeListBuilder.create().texOffs(0, 17).addBox(-6.5F, 7.0F, 3.0F, 3.0F, 5.0F, 4.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData3.addOrReplaceChild("backPocket", CubeListBuilder.create().texOffs(14, 20).addBox(-3.0F, 1.0F, 8.0F, 6.0F, 8.0F, 2.0F), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isCrouching()) {
            root.xRot = 0.5F;
            root.y = 3.2F;
        } else {
            root.xRot = 0.0F;
            root.y = 0.0F;
        }
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(root);
    }
}
