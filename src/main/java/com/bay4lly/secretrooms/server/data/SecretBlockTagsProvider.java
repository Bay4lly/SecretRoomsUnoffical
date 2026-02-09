package com.bay4lly.secretrooms.server.data;

import com.bay4lly.secretrooms.SecretRooms6;
import com.bay4lly.secretrooms.server.blocks.SecretBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class SecretBlockTagsProvider extends BlockTagsProvider {
    public SecretBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, SecretRooms6.MODID, existingFileHelper);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(SecretBlockTags.ONE_WAY_GLASS_CULL)
            .add(SecretBlocks.ONE_WAY_GLASS.get())
            .addTag(BlockTags.IMPERMEABLE);
    }
}
