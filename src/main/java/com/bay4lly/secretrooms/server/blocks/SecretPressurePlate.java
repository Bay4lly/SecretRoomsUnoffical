package com.bay4lly.secretrooms.server.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty; // Dogru paket yolu burasi
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.function.Predicate;

public class SecretPressurePlate extends AbstractSecretPressurePlateBase {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    private final Predicate<Entity> entityPredicate;

    public SecretPressurePlate(Properties properties, Predicate<Entity> entityPredicate) {
        super(properties);
        this.entityPredicate = entityPredicate;
        this.registerDefaultState(this.defaultBlockState().setValue(POWERED, false));
    }

    @Override
    protected int getSignalForState(BlockState state) {
        return state.getValue(POWERED) ? 15 : 0;
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClientSide && !state.getValue(POWERED)) {
            this.checkPressed(world, pos, state);
        }
        super.stepOn(world, pos, state, entity);
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (!world.isClientSide && !state.getValue(POWERED)) {
            this.checkPressed(world, pos, state);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!world.isClientSide && state.getValue(POWERED)) {
            this.checkPressed(world, pos, state);
        }
    }

    private void checkPressed(Level world, BlockPos pos, BlockState state) {
        // Blok sert kalsin ama uzerindekini algilasin diye 1 blok yukariya bakiyoruz
        AABB detectionArea = new AABB(pos).move(0, 1.0, 0).inflate(0.1, 0.1, 0.1);
        
        List<? extends Entity> list = world.getEntitiesOfClass(Entity.class, detectionArea, this.entityPredicate);
        boolean hasEntity = !list.isEmpty();
        boolean isCurrentlyPowered = state.getValue(POWERED);

        if (hasEntity != isCurrentlyPowered) {
            BlockState newState = state.setValue(POWERED, hasEntity);
            world.setBlock(pos, newState, 3);
            this.updateNeighbors(world, pos);
            this.playSound(world, pos, hasEntity);
        }

        if (hasEntity) {
            world.scheduleTick(pos, this, 10);
        }
    }

    protected void playSound(LevelAccessor world, BlockPos pos, boolean pressed) {
        world.playSound(null, pos, pressed ? SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON : SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, 
                        SoundSource.BLOCKS, 0.3F, pressed ? 0.6F : 0.5F);
    }

    private void updateNeighbors(Level world, BlockPos pos) {
        world.updateNeighborsAt(pos, this);
        world.updateNeighborsAt(pos.below(), this);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!isMoving && state.getBlock() != newState.getBlock()) {
            if (state.getValue(POWERED)) {
                this.updateNeighbors(world, pos);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(POWERED);
    }
}