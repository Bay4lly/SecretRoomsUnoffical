package com.bay4lly.secretrooms.server.data;

import com.bay4lly.secretrooms.SecretRooms6;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

import static com.bay4lly.secretrooms.server.blocks.SecretBlocks.*;
import static com.bay4lly.secretrooms.server.items.SecretItems.CAMOUFLAGE_PASTE;
import static com.bay4lly.secretrooms.server.items.SecretItems.SWITCH_PROBE;
import static com.bay4lly.secretrooms.server.items.SecretItems.TRUE_VISION_GOGGLES;

public class SecretRecipeProvider extends RecipeProvider {
    public SecretRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        Item camoPaste = CAMOUFLAGE_PASTE.get();
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, TORCH_LEVER.get()).define('T', Items.TORCH).define('L', Items.LEVER).pattern("T").pattern("L").group("torch_lever.json").unlockedBy("has_torch_lever", hasItems(Items.TORCH, Items.LEVER)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, camoPaste, 9).define('X', Tags.Items.DYES).define('#', Items.CLAY_BALL).pattern("XXX").pattern("X#X").pattern("XXX").group("camouflage_paste").unlockedBy("has_earth_item", has(SecretItemTags.EARTH_ITEM)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GHOST_BLOCK.get(), 4).define('X', camoPaste).define('0', SecretItemTags.SECRET_RECIPE_ITEMS).pattern("X0X").pattern("0 0").pattern("X0X").group("ghost_block").unlockedBy("has_camo", has(camoPaste)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ONE_WAY_GLASS.get(), 9).define('X', camoPaste).define('0', SecretItemTags.CLEAR_GLASS).pattern("XXX").pattern("000").pattern("000").group("one_way_glass").unlockedBy("has_camo", has(camoPaste)).save(consumer, new ResourceLocation(SecretRooms6.MODID, "one_way_glass_up"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ONE_WAY_GLASS.get(), 9).define('X', camoPaste).define('0', SecretItemTags.CLEAR_GLASS).pattern("000").pattern("000").pattern("XXX").group("one_way_glass").unlockedBy("has_camo", has(camoPaste)).save(consumer, new ResourceLocation(SecretRooms6.MODID, "one_way_glass_down"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ONE_WAY_GLASS.get(), 9).define('X', camoPaste).define('0', SecretItemTags.CLEAR_GLASS).pattern("X00").pattern("X00").pattern("X00").group("one_way_glass").unlockedBy("has_camo", has(camoPaste)).save(consumer, new ResourceLocation(SecretRooms6.MODID, "one_way_glass_left"));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ONE_WAY_GLASS.get(), 9).define('X', camoPaste).define('0', SecretItemTags.CLEAR_GLASS).pattern("00X").pattern("00X").pattern("00X").group("one_way_glass").unlockedBy("has_camo", has(camoPaste)).save(consumer, new ResourceLocation(SecretRooms6.MODID, "one_way_glass_right"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, SWITCH_PROBE.get()).requires(camoPaste).requires(Items.REDSTONE_TORCH).group("switch_probe").unlockedBy("has_camo", has(camoPaste)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, ONE_WAY_GLASS.get()).requires(camoPaste).requires(SecretItemTags.CLEAR_GLASS).group("one_way_glass_shapeless").unlockedBy("has_camo", has(camoPaste)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, SECRET_CHEST.get()).requires(camoPaste).requires(Tags.Items.CHESTS_WOODEN).group("secret_chest").unlockedBy("has_camo", has(camoPaste)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, SECRET_PRESSURE_PLATE.get()).requires(camoPaste).requires(ItemTags.WOODEN_PRESSURE_PLATES).group("secret_pressure_plate").unlockedBy("has_camo", has(camoPaste)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, SECRET_PLAYER_PRESSURE_PLATE.get()).requires(camoPaste).requires(Items.STONE_PRESSURE_PLATE).group("secret_player_pressure_plate").unlockedBy("has_camo", has(camoPaste)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, SECRET_DOOR.get()).requires(camoPaste).requires(ItemTags.WOODEN_DOORS).group("secret_door").unlockedBy("has_camo", has(camoPaste)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, SECRET_IRON_DOOR.get()).requires(camoPaste).requires(Items.IRON_DOOR).group("secret_iron_door").unlockedBy("has_camo", has(camoPaste)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, SECRET_LEVER.get()).requires(camoPaste).requires(Items.LEVER).group("secret_lever").unlockedBy("has_camo", has(camoPaste)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, SECRET_DAYLIGHT_DETECTOR.get()).requires(camoPaste).requires(Items.DAYLIGHT_DETECTOR).group("secret_daylight_detector").unlockedBy("has_camo", has(camoPaste)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, SECRET_REDSTONE.get()).requires(camoPaste).requires(Items.REDSTONE).group("secret_redstone").unlockedBy("has_camo", has(camoPaste)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, SECRET_STAIRS.get()).requires(camoPaste).requires(ItemTags.STAIRS).group("secret_stairs").unlockedBy("has_camo", has(camoPaste)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, SECRET_WOODEN_BUTTON.get()).requires(camoPaste).requires(ItemTags.WOODEN_BUTTONS).group("secret_wooden_button").unlockedBy("has_camo", has(camoPaste)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, SECRET_STONE_BUTTON.get()).requires(camoPaste).requires(Items.STONE_BUTTON).group("secret_stone_button").unlockedBy("has_camo", has(camoPaste)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, SECRET_TRAPPED_CHEST.get()).requires(camoPaste).requires(Tags.Items.CHESTS_TRAPPED).group("secret_trapped_chest").unlockedBy("has_camo", has(camoPaste)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, SECRET_TRAPDOOR.get()).requires(camoPaste).requires(ItemTags.WOODEN_TRAPDOORS).group("secret_trapdoor").unlockedBy("has_camo", has(camoPaste)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, SECRET_IRON_TRAPDOOR.get()).requires(camoPaste).requires(Items.IRON_TRAPDOOR).group("secret_iron_trapdoor").unlockedBy("has_camo", has(camoPaste)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, SECRET_OBSERVER.get()).requires(camoPaste).requires(Items.OBSERVER).group("secret_observer").unlockedBy("has_camo", has(camoPaste)).save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, SECRET_CLAMBER.get()).requires(camoPaste).requires(Items.LADDER).group("secret_clamber").unlockedBy("has_camo", has(camoPaste)).save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, SECRET_GATE.get()).define('X', ItemTags.PLANKS).define('0', camoPaste).define('R', Items.REDSTONE).define('A', Items.ENDER_PEARL).pattern("X0X").pattern("0A0").pattern("XRX").group("secret_gate").unlockedBy("has_camo", has(camoPaste)).save(consumer);
    }

    private InventoryChangeTrigger.TriggerInstance hasItems(ItemLike... itemIn) {
        ItemPredicate.Builder builder = ItemPredicate.Builder.item();
        for (ItemLike provider : itemIn) {
            builder.of(provider);
        }
        return inventoryTrigger(builder.build());
    }
}

