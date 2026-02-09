package com.bay4lly.secretrooms.server.items;

import com.bay4lly.secretrooms.SecretRooms6;
import com.bay4lly.secretrooms.server.blocks.SecretBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.Objects;

public class SecretItems {

    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(BuiltInRegistries.ITEM, SecretRooms6.MODID);

    public static final DeferredHolder<Item, Item> GHOST_BLOCK = REGISTRY.register("ghost_block", () -> new SecretBlockItem(SecretBlocks.GHOST_BLOCK.get(), prop()));
    public static final DeferredHolder<Item, Item> SECRET_STAIRS = REGISTRY.register("secret_stairs", () -> new SecretBlockItem(SecretBlocks.SECRET_STAIRS.get(), prop()));
    public static final DeferredHolder<Item, Item> SECRET_LEVER = REGISTRY.register("secret_lever", () -> new SecretBlockItem(SecretBlocks.SECRET_LEVER.get(), prop()));
    public static final DeferredHolder<Item, Item> SECRET_REDSTONE = REGISTRY.register("secret_redstone", () -> new SecretBlockItem(SecretBlocks.SECRET_REDSTONE.get(), prop()));
    public static final DeferredHolder<Item, Item> ONE_WAY_GLASS = REGISTRY.register("one_way_glass", () -> new SecretBlockItem(SecretBlocks.ONE_WAY_GLASS.get(), prop()));
    public static final DeferredHolder<Item, Item> SECRET_WOODEN_BUTTON = REGISTRY.register("secret_wooden_button", () -> new SecretBlockItem(SecretBlocks.SECRET_WOODEN_BUTTON.get(), prop()));
    public static final DeferredHolder<Item, Item> SECRET_STONE_BUTTON = REGISTRY.register("secret_stone_button", () -> new SecretBlockItem(SecretBlocks.SECRET_STONE_BUTTON.get(), prop()));
    public static final DeferredHolder<Item, Item> TORCH_LEVER = REGISTRY.register("torch_lever", () -> new StandingAndWallBlockItem(Objects.requireNonNull(SecretBlocks.TORCH_LEVER.get()), Objects.requireNonNull(SecretBlocks.WALL_TORCH_LEVER.get()), prop(), net.minecraft.core.Direction.DOWN));
    public static final DeferredHolder<Item, Item> SECRET_PRESSURE_PLATE = REGISTRY.register("secret_pressure_plate", () -> new SecretBlockItem(SecretBlocks.SECRET_PRESSURE_PLATE.get(), prop()));
    public static final DeferredHolder<Item, Item> SECRET_PLAYER_PRESSURE_PLATE = REGISTRY.register("secret_player_pressure_plate", () -> new SecretBlockItem(SecretBlocks.SECRET_PLAYER_PRESSURE_PLATE.get(), prop()));
    public static final DeferredHolder<Item, Item> SECRET_DOOR = REGISTRY.register("secret_door", () -> new SecretDoubleBlockItem(SecretBlocks.SECRET_DOOR.get(), prop()));
    public static final DeferredHolder<Item, Item> SECRET_IRON_DOOR = REGISTRY.register("secret_iron_door", () -> new SecretDoubleBlockItem(SecretBlocks.SECRET_IRON_DOOR.get(), prop()));
    public static final DeferredHolder<Item, Item> SECRET_CHEST = REGISTRY.register("secret_chest", () -> new SecretBlockItem(SecretBlocks.SECRET_CHEST.get(), prop()));
    public static final DeferredHolder<Item, Item> SECRET_TRAPDOOR = REGISTRY.register("secret_trapdoor", () -> new SecretBlockItem(SecretBlocks.SECRET_TRAPDOOR.get(), prop()));
    public static final DeferredHolder<Item, Item> SECRET_IRON_TRAPDOOR = REGISTRY.register("secret_iron_trapdoor", () -> new SecretBlockItem(SecretBlocks.SECRET_IRON_TRAPDOOR.get(), prop()));
    public static final DeferredHolder<Item, Item> SECRET_TRAPPED_CHEST = REGISTRY.register("secret_trapped_chest", () -> new SecretBlockItem(SecretBlocks.SECRET_TRAPPED_CHEST.get(), prop()));
    public static final DeferredHolder<Item, Item> SECRET_GATE = REGISTRY.register("secret_gate", () -> new SecretBlockItem(SecretBlocks.SECRET_GATE.get(), prop()));
    public static final DeferredHolder<Item, Item> SECRET_DUMMY_BLOCK = REGISTRY.register("secret_dummy_block", () -> new SecretBlockItem(SecretBlocks.SECRET_DUMMY_BLOCK.get(), prop()));
    public static final DeferredHolder<Item, Item> SECRET_DAYLIGHT_DETECTOR = REGISTRY.register("secret_daylight_detector", () -> new SecretBlockItem(SecretBlocks.SECRET_DAYLIGHT_DETECTOR.get(), prop()));
    public static final DeferredHolder<Item, Item> SECRET_OBSERVER = REGISTRY.register("secret_observer", () -> new SecretBlockItem(SecretBlocks.SECRET_OBSERVER.get(), prop()));
    public static final DeferredHolder<Item, Item> SECRET_CLAMBER = REGISTRY.register("secret_clamber", () -> new SecretBlockItem(SecretBlocks.SECRET_CLAMBER.get(), prop()));

    public static final DeferredHolder<Item, Item> CAMOUFLAGE_PASTE = REGISTRY.register("camouflage_paste", () -> new Item(prop()));
    public static final DeferredHolder<Item, Item> SWITCH_PROBE = REGISTRY.register("switch_probe", () -> new SwitchProbe(prop()));

    public static final DeferredHolder<Item, TrueVisionGoggles> TRUE_VISION_GOGGLES = REGISTRY.register("true_vision_goggles", () -> new TrueVisionGoggles(prop().durability(900)));

    private static Item.Properties prop() {
        return new Item.Properties();
    }
    
    // Register items to creative tab
    public static void addCreativeItems(CreativeModeTab.Output output) {
        output.accept(GHOST_BLOCK.get());
        output.accept(SECRET_STAIRS.get());
        output.accept(SECRET_LEVER.get());
        output.accept(SECRET_REDSTONE.get());
        output.accept(ONE_WAY_GLASS.get());
        output.accept(SECRET_WOODEN_BUTTON.get());
        output.accept(SECRET_STONE_BUTTON.get());
        output.accept(TORCH_LEVER.get());
        output.accept(SECRET_PRESSURE_PLATE.get());
        output.accept(SECRET_PLAYER_PRESSURE_PLATE.get());
        output.accept(SECRET_DOOR.get());
        output.accept(SECRET_IRON_DOOR.get());
        output.accept(SECRET_CHEST.get());
        output.accept(SECRET_TRAPDOOR.get());
        output.accept(SECRET_IRON_TRAPDOOR.get());
        output.accept(SECRET_TRAPPED_CHEST.get());
        output.accept(SECRET_GATE.get());
        output.accept(SECRET_DUMMY_BLOCK.get());
        output.accept(SECRET_DAYLIGHT_DETECTOR.get());
        output.accept(SECRET_OBSERVER.get());
        output.accept(SECRET_CLAMBER.get());
        output.accept(CAMOUFLAGE_PASTE.get());
        output.accept(SWITCH_PROBE.get());
        output.accept(TRUE_VISION_GOGGLES.get());
    }
}
