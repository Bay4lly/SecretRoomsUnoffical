package com.bay4lly.secretrooms;

import com.bay4lly.secretrooms.client.SecretModelHandler;
import com.bay4lly.secretrooms.client.model.OneWayGlassModel;
import com.bay4lly.secretrooms.client.model.quads.TrueVisionBakedQuad;
import com.bay4lly.secretrooms.server.blocks.SecretBaseBlock;
import com.bay4lly.secretrooms.server.blocks.SecretBlocks;
import com.bay4lly.secretrooms.server.data.SecretBlockLootTableProvider;
import com.bay4lly.secretrooms.server.data.SecretBlockTagsProvider;
import com.bay4lly.secretrooms.server.data.SecretItemTagsProvider;
import com.bay4lly.secretrooms.server.data.SecretRecipeProvider;
import com.bay4lly.secretrooms.server.items.*;
import com.bay4lly.secretrooms.server.tileentity.SecretTileEntities;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SecretRooms6.MODID)
public class SecretRooms6 {
    public static final String MODID = "secretroomsmod";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB = CREATIVE_TABS.register("tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MODID))
            .icon(() -> new ItemStack(SecretItems.CAMOUFLAGE_PASTE.get()))
            .displayItems((params, output) -> SecretItems.addCreativeItems(output))
            .build());

    public SecretRooms6(IEventBus bus) {
        IEventBus forgeBus = NeoForge.EVENT_BUS;
        
        bus.addListener(this::gatherData);
        CREATIVE_TABS.register(bus);
        SecretBlocks.REGISTRY.register(bus);
        SecretItems.REGISTRY.register(bus);
        SecretTileEntities.REGISTRY.register(bus);

        bus.addListener(this::onRegisterReloads);
        forgeBus.addListener(this::modifyBreakSpeed);
        forgeBus.addListener(TrueVisionGogglesHandler::onLootTableLoad);
        forgeBus.addListener(TrueVisionGogglesHandler::onPlayerTick);

        if (net.neoforged.fml.loading.FMLEnvironment.dist == Dist.CLIENT) {
            bus.addListener(SecretModelHandler::onBlockColors);
            //bus.addListener(SecretModelHandler::onModelBaked);
            bus.addListener(SecretModelHandler::onEntityModelRegistered);
            bus.addListener(OneWayGlassModel::onModelsReady);
            
            // TextureStitchEvent artik BUS.MOD üzerinden yürür
            bus.addListener(TrueVisionBakedQuad::onTextureStitch);
            bus.addListener(SwitchProbe::onRegisterTooltipFactories);

            forgeBus.addListener(SwitchProbe::appendHover);
            forgeBus.addListener(TrueVisionGogglesClientHandler::onClientWorldLoad);
            forgeBus.addListener(TrueVisionGogglesClientHandler::onClientWorldTick);
        }
    }

    private boolean recurse = false;
    public void modifyBreakSpeed(PlayerEvent.BreakSpeed event) {
        if(recurse) return;
        recurse = true;
        Player player = event.getEntity();
        event.getPosition().ifPresent(pos -> 
            SecretBaseBlock.getMirrorState(player.level(), pos).ifPresent(mirror -> 
                event.setNewSpeed(player.getDigSpeed(mirror, pos))
            )
        );
        recurse = false;
    }

    public void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var output = generator.getPackOutput();
        var lookupProvider = event.getLookupProvider();
        var helper = event.getExistingFileHelper();

        SecretBlockTagsProvider blockTags = new SecretBlockTagsProvider(output, lookupProvider, helper);
        generator.addProvider(event.includeServer(), blockTags);
        
        // 1.20.1 FIX: asTagLookup hatasi icin blockTags referansi kullaniliyor
        generator.addProvider(event.includeServer(), new SecretItemTagsProvider(output, lookupProvider, blockTags.contentsGetter(), helper));
        generator.addProvider(event.includeServer(), new SecretRecipeProvider(output, lookupProvider));
        generator.addProvider(event.includeServer(), new SecretBlockLootTableProvider(output, lookupProvider));
    }

    public void onRegisterReloads(RegisterClientReloadListenersEvent event) {
        ResourceManagerReloadListener listener = rm -> {
            TrueVisionGoggles goggles = (TrueVisionGoggles) SecretItems.TRUE_VISION_GOGGLES.get();
            if (goggles != null) {
                goggles.refreshArmorModel();
            }
        };
        event.registerReloadListener(listener);
    }
}
