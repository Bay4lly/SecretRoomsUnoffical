package com.bay4lly.secretrooms.client.model;

import com.bay4lly.secretrooms.SecretRooms6;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class TrueVisionGogglesModel extends HumanoidModel<LivingEntity> {

    // 1.21'de ResourceLocation oluşturma yöntemi güncellendi.
    public static final ModelLayerLocation TRUE_VISION_GOGGLES_MODEL = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(SecretRooms6.MODID, "true_vision"), "main");

    public TrueVisionGogglesModel(ModelPart part) {
        // 1.21 HumanoidModel kurucusu artık RenderType factory almaz.
        // Şeffaflık texture (doku) üzerinden otomatik halledilir.
        super(part);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        // ImmutableList yerine Java List.of kullanımı
        return List.of();
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return List.of(this.head, this.hat);
    }

    // Bu metod modelin geometrisini tanımlar.
    // Kayıt işlemini ana ClientEventSubscriber sınıfınızda yapmanız önerilir.
    public static LayerDefinition createBodyLayer() {
        CubeDeformation deformation = CubeDeformation.NONE;
        float yOff = 0.0F;
        
        MeshDefinition mesh = HumanoidModel.createMesh(deformation, yOff);
        PartDefinition root = mesh.getRoot();

        // "hat" parçasını, gözlük modeline uyacak şekilde güncelliyoruz.
        // createMesh metodunu LayerDefinition döndürecek şekilde standartlaştırdım.
        root.addOrReplaceChild("hat", 
                CubeListBuilder.create()
                        .texOffs(32, 0)
                        .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, deformation.extend(0.2F)), 
                PartPose.offset(0.0F, 0.0F + yOff, 0.0F));

        return LayerDefinition.create(mesh, 64, 32); // Texture boyutu varsayılan olarak genellikle 64x32 veya 64x64'tür.
    }
}