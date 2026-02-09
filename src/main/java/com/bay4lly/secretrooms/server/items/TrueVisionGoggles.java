package com.bay4lly.secretrooms.server.items;

import com.bay4lly.secretrooms.SecretRooms6;
import com.bay4lly.secretrooms.client.model.TrueVisionGogglesModel;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Consumer;

public class TrueVisionGoggles extends ArmorItem {

    public static final Holder<ArmorMaterial> DUMMY_MATERIAL = new Holder.Direct<>(new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 0);
                map.put(ArmorItem.Type.LEGGINGS, 0);
                map.put(ArmorItem.Type.CHESTPLATE, 0);
                map.put(ArmorItem.Type.HELMET, 0);
                map.put(ArmorItem.Type.BODY, 0);
            }),
            0,
            SoundEvents.ARMOR_EQUIP_CHAIN,
            () -> Ingredient.EMPTY,
            // Bu isim 'assets/secretrooms/textures/models/armor/true_vision_goggles_layer_1.png' dosyasını arar.
            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(SecretRooms6.MODID, "true_vision_goggles"))),
            0.0F,
            0.0F
    ));

    @OnlyIn(Dist.CLIENT)
    private TrueVisionGogglesModel model;

    public TrueVisionGoggles(Properties properties) {
        super(DUMMY_MATERIAL, Type.HELMET, properties);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (model == null) {
                    refreshArmorModel();
                }

                // Temel animasyon özelliklerini kopyalar (eğilme, boy vb.)
                model.young = original.young;
                model.riding = original.riding;
                model.crouching = original.crouching;

                // Gözlüğün kafada durması için pozisyonu head parçasına kopyalarız
                model.head.copyFrom(original.head);
                model.hat.copyFrom(original.head); 

                return model;
            }
        });
    }

    @OnlyIn(Dist.CLIENT)
    public void refreshArmorModel() {
        this.model = new TrueVisionGogglesModel(Minecraft.getInstance().getEntityModels().bakeLayer(TrueVisionGogglesModel.TRUE_VISION_GOGGLES_MODEL));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}