package com.bay4lly.secretrooms.server.data;

import com.bay4lly.secretrooms.SecretRooms6;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.bay4lly.secretrooms.server.data.SecretItemTags.*;

public class SecretItemTagsProvider extends ItemTagsProvider {

    public SecretItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, SecretRooms6.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(EARTH_ITEM).add(Items.CLAY_BALL, Items.DIRT, Items.SAND);
        this.tag(SECRET_RECIPE_ITEMS).add(Items.ROTTEN_FLESH).addTags(ItemTags.WOOL);
        this.tag(CLEAR_GLASS).add(Items.GLASS);
    }
}