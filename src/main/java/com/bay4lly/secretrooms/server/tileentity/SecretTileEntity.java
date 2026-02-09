package com.bay4lly.secretrooms.server.tileentity;

import com.bay4lly.secretrooms.client.SecretModelData;
import com.bay4lly.secretrooms.server.blocks.SecretBaseBlock;
import com.bay4lly.secretrooms.server.data.SecretData;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SecretTileEntity extends BlockEntity {

    private final SecretData data = new SecretData(this);
    
    @Nullable
    private CompoundTag pendingSecretData;

    public SecretTileEntity(BlockPos p_155115_, BlockState p_155116_) {
        super(SecretTileEntities.SECRET_TILE_ENTITY.get(), p_155115_, p_155116_);
    }

    public SecretTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void setLevel(net.minecraft.world.level.Level level) {
        super.setLevel(level);
        // Eğer level atandığında pending data varsa, şimdi yükle
        if (this.pendingSecretData != null && level != null) {
            this.data.readNBT(this.pendingSecretData, level.registryAccess());
            this.pendingSecretData = null;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compound, net.minecraft.core.HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.put("secret_data", this.data.writeNBT(new CompoundTag()));
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, net.minecraft.core.HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);
        CompoundTag secretData = nbt.getCompound("secret_data");
        if (!secretData.isEmpty()) {
            if (this.level != null) {
                this.data.readNBT(secretData, this.level.registryAccess());
            } else {
                // Level henüz atanmamış - daha sonra setLevel() çağrıldığında yüklenir
                this.pendingSecretData = secretData;
            }
        }
    }

    @Override
    public CompoundTag getUpdateTag(net.minecraft.core.HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }


    @Override
    protected void applyImplicitComponents(BlockEntity.DataComponentInput componentInput) {
        super.applyImplicitComponents(componentInput);
    }

    @Override
    public void onDataPacket(net.minecraft.network.Connection net, net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket pkt, net.minecraft.core.HolderLookup.Provider lookupProvider) {
        if (this.level != null) {
            this.data.readNBT(pkt.getTag(), lookupProvider);
        }
    }

    @Nonnull
    @Override
    public ModelData getModelData() {
        if(this.remove) {
            return super.getModelData();
        }
        ModelData.Builder builder = ModelData.builder()
                .with(SecretModelData.SRM_BLOCKSTATE, this.data.getBlockState());
        BlockState state = this.level.getBlockState(this.worldPosition);
        if(state.getBlock() instanceof SecretBaseBlock) {
            ((SecretBaseBlock) state.getBlock()).applyExtraModelData(this.level, this.worldPosition, state, builder);
        }
        return builder.build();
    }

    public SecretData getData() {
        return this.data;
    }
}
