package com.bay4lly.secretrooms.client;

import com.bay4lly.secretrooms.SecretRooms6; // 6 olarak düzeltildi
import com.bay4lly.secretrooms.client.model.OneWayGlassModel;
import com.bay4lly.secretrooms.client.model.SecretBlockModel;
import com.bay4lly.secretrooms.client.model.SecretMappedModel;
import com.bay4lly.secretrooms.client.model.TrueVisionGogglesModel;
import com.bay4lly.secretrooms.client.world.DelegateWorld;
import com.bay4lly.secretrooms.server.blocks.SecretBaseBlock;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation; // ModelResourceLocation eklendi
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.function.Function;

import static com.bay4lly.secretrooms.server.blocks.SecretBlocks.*;

@Mod.EventBusSubscriber(modid = SecretRooms6.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SecretModelHandler {

    @SubscribeEvent
    public static void onEntityModelRegistered(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(TrueVisionGogglesModel.TRUE_VISION_GOGGLES_MODEL, () -> LayerDefinition.create(TrueVisionGogglesModel.createMesh(CubeDeformation.NONE, 0.0F), 64, 32));
    }

    @SubscribeEvent
    public static void onBlockColors(RegisterColorHandlersEvent.Block event) {
        BlockColors colors = event.getBlockColors();
        colors.register((state, world, pos, index) -> SecretBaseBlock.getMirrorState(world, pos).map(DelegateWorld.createFunction(world, (reader, mirror) -> colors.getColor(mirror, reader, pos, index))).orElse(-1),
            GHOST_BLOCK.get(), SECRET_STAIRS.get(), SECRET_LEVER.get(), SECRET_REDSTONE.get(), ONE_WAY_GLASS.get(), SECRET_WOODEN_BUTTON.get(),
            SECRET_STONE_BUTTON.get(), SECRET_PRESSURE_PLATE.get(), SECRET_PLAYER_PRESSURE_PLATE.get(), SECRET_DOOR.get(), SECRET_IRON_DOOR.get(),
            SECRET_CHEST.get(), SECRET_TRAPDOOR.get(), SECRET_IRON_TRAPDOOR.get(), SECRET_TRAPPED_CHEST.get(), SECRET_GATE.get(), SECRET_DUMMY_BLOCK.get(),
            SECRET_DAYLIGHT_DETECTOR.get(), SECRET_OBSERVER.get(), SECRET_CLAMBER.get()
        );
    }

    @SubscribeEvent
    public static void onModelModify(ModelEvent.ModifyBakingResult event) {
        // Hata veren kısım burasıydı, ResourceLocation olarak geri çektik
        Map<ResourceLocation, BakedModel> registry = event.getModels();

        put(registry, SecretBlockModel::new,
            GHOST_BLOCK.get(), SECRET_STAIRS.get(), SECRET_LEVER.get(), SECRET_REDSTONE.get(), SECRET_WOODEN_BUTTON.get(), SECRET_STONE_BUTTON.get(),
            SECRET_PRESSURE_PLATE.get(), SECRET_PLAYER_PRESSURE_PLATE.get(), SECRET_CHEST.get(), SECRET_TRAPPED_CHEST.get(), SECRET_GATE.get(),
            SECRET_DUMMY_BLOCK.get(), SECRET_DAYLIGHT_DETECTOR.get(), SECRET_OBSERVER.get(), SECRET_CLAMBER.get()
        );

        put(registry, SecretMappedModel::new, SECRET_DOOR.get(), SECRET_IRON_DOOR.get(), SECRET_TRAPDOOR.get(), SECRET_IRON_TRAPDOOR.get());
        put(registry, OneWayGlassModel::new, ONE_WAY_GLASS.get());
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(TORCH_LEVER.get(), net.minecraft.client.renderer.RenderType.cutout());
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(WALL_TORCH_LEVER.get(), net.minecraft.client.renderer.RenderType.cutout());
        });
    }

    private static void put(Map<ResourceLocation, BakedModel> registry, Function<BakedModel, BakedModel> creator, Block... blocks) {
        for (Block block : blocks) {
            for (BlockState state : block.getStateDefinition().getPossibleStates()) {
                ModelResourceLocation loc = BlockModelShaper.stateToModelLocation(state);
                BakedModel existingModel = registry.get(loc);
                if (existingModel != null) {
                    registry.put(loc, creator.apply(existingModel));
                }
            }
        }
    }
}
