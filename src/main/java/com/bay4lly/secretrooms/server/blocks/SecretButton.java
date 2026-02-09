package com.bay4lly.secretrooms.server.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.List;

public class SecretButton extends SecretBaseBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    private final boolean wooden;

    public SecretButton(Properties properties, boolean wooden) {
        super(properties);
        this.wooden = wooden;
        this.registerDefaultState(this.defaultBlockState().setValue(POWERED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(POWERED);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level worldIn, BlockPos pos, Player player, BlockHitResult hit) {
        if (state.getValue(POWERED)) return InteractionResult.SUCCESS;
        worldIn.setBlock(pos, state.setValue(POWERED, true), 3);
        this.playSound(player, worldIn, pos, true);
        this.updateNeighbors(worldIn, pos);
        worldIn.scheduleTick(pos, this, this.wooden ? 30 : 20);
        return InteractionResult.SUCCESS;
    }

    private void playSound(@Nullable Player player, LevelAccessor world, BlockPos pos, boolean turnOn) {
        SoundEvent sound = turnOn ? (this.wooden ? SoundEvents.WOODEN_BUTTON_CLICK_ON : SoundEvents.STONE_BUTTON_CLICK_ON) 
                                 : (this.wooden ? SoundEvents.WOODEN_BUTTON_CLICK_OFF : SoundEvents.STONE_BUTTON_CLICK_OFF);
        world.playSound(turnOn ? player : null, pos, sound, SoundSource.BLOCKS, 0.3F, turnOn ? 0.6F : 0.5F);
    }

    @Override
    public int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return blockState.getValue(POWERED) ? 15 : 0;
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        if (!worldIn.isClientSide && state.getValue(POWERED)) {
            if (this.wooden) {
                this.checkPressed(state, worldIn, pos);
            } else {
                worldIn.setBlock(pos, state.setValue(POWERED, false), 3);
                this.updateNeighbors(worldIn, pos);
                this.playSound(null, worldIn, pos, false);
            }
        }
    }

    private void checkPressed(BlockState state, Level worldIn, BlockPos pos) {
        List<? extends Entity> list = worldIn.getEntitiesOfClass(AbstractArrow.class, state.getShape(worldIn, pos).bounds().move(pos));
        boolean flag = !list.isEmpty();
        if (flag != state.getValue(POWERED)) {
            worldIn.setBlock(pos, state.setValue(POWERED, flag), 3);
            this.updateNeighbors(worldIn, pos);
            this.playSound(null, worldIn, pos, flag);
        }
        if (flag) worldIn.scheduleTick(pos, this, 30);
    }

    private void updateNeighbors(Level world, BlockPos pos) {
        world.updateNeighborsAt(pos, this);
        for (Direction value : Direction.values()) world.updateNeighborsAt(pos.relative(value), this);
    }
}