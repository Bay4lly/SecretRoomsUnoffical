package com.bay4lly.secretrooms.server.data;

import com.google.common.collect.ImmutableList;
import com.bay4lly.secretrooms.server.items.SecretItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import static com.bay4lly.secretrooms.server.blocks.SecretBlocks.*;

public class SecretBlockLootTableProvider extends LootTableProvider {

    public SecretBlockLootTableProvider(PackOutput output) {
        super(output, Set.of(), ImmutableList.of(
            new LootTableProvider.SubProviderEntry(SecretRoomsBlockLootTables::new, LootContextParamSets.BLOCK)
        ));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
        map.forEach((res, table) -> table.validate(validationtracker));
    }

    private static class SecretRoomsBlockLootTables implements LootTableSubProvider {

        @Override
        public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            addTables(consumer);
        }
        
        private void addTables(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            createBlockDrop(consumer, GHOST_BLOCK);
            createBlockDrop(consumer, SECRET_STAIRS);
            createBlockDrop(consumer, SECRET_LEVER);
            createBlockDrop(consumer, SECRET_REDSTONE);
            createBlockDrop(consumer, ONE_WAY_GLASS);
            createBlockDrop(consumer, SECRET_WOODEN_BUTTON);
            createBlockDrop(consumer, SECRET_STONE_BUTTON);
            createBlockDrop(consumer, TORCH_LEVER);
            createBlockDrop(consumer, WALL_TORCH_LEVER, SecretItems.TORCH_LEVER);
            createBlockDrop(consumer, SECRET_PRESSURE_PLATE);
            createBlockDrop(consumer, SECRET_PLAYER_PRESSURE_PLATE);
            createDoorItemTable(consumer, SECRET_DOOR);
            createDoorItemTable(consumer, SECRET_IRON_DOOR);
            createBlockDrop(consumer, SECRET_CHEST);
            createBlockDrop(consumer, SECRET_TRAPDOOR);
            createBlockDrop(consumer, SECRET_IRON_TRAPDOOR);
            createBlockDrop(consumer, SECRET_TRAPPED_CHEST);
            createBlockDrop(consumer, SECRET_GATE);
            createBlockDrop(consumer, SECRET_DAYLIGHT_DETECTOR);
            createBlockDrop(consumer, SECRET_OBSERVER);
            createBlockDrop(consumer, SECRET_CLAMBER);
        }

        private void createBlockDrop(BiConsumer<ResourceLocation, LootTable.Builder> consumer, Supplier<Block> block) {
            createBlockDrop(consumer, block, block);
        }

        private void createBlockDrop(BiConsumer<ResourceLocation, LootTable.Builder> consumer, Supplier<Block> block, Supplier<? extends ItemLike> item) {
            consumer.accept(block.get().getLootTable(), createSingleItemTable(item.get()));
        }

        private LootTable.Builder createSingleItemTable(ItemLike item) {
            return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(item))
                    .when(ExplosionCondition.survivesExplosion())
                );
        }

        private void createDoorItemTable(BiConsumer<ResourceLocation, LootTable.Builder> consumer, Supplier<Block> block) {
            Block doorBlock = block.get();
            consumer.accept(doorBlock.getLootTable(), createSingleItemTable(doorBlock.asItem()));
        }
    }
}