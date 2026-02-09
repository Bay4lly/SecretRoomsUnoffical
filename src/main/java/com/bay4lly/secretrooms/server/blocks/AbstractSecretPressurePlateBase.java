package com.bay4lly.secretrooms.server.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractSecretPressurePlateBase extends SecretBaseBlock {
    protected AbstractSecretPressurePlateBase(Properties properties) {
        super(properties);
    }

    @Override
    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return this.getSignalForState(state);
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    protected abstract int getSignalForState(BlockState state);
}