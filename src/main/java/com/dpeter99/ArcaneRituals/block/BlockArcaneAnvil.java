package com.dpeter99.ArcaneRituals.block;

import com.dpeter99.ArcaneRituals.tileentity.WitchAltarTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class BlockArcaneAnvil extends Block {

    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    private static final VoxelShape PART_BASE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 4.0D, 14.0D);
    private static final VoxelShape PART_LOWER_X = Block.makeCuboidShape(3.0D, 4.0D, 4.0D, 13.0D, 5.0D, 12.0D);
    private static final VoxelShape PART_MID_X = Block.makeCuboidShape(4.0D, 5.0D, 6.0D, 12.0D, 10.0D, 10.0D);
    private static final VoxelShape PART_UPPER_X = Block.makeCuboidShape(0.0D, 10.0D, 3.0D, 16.0D, 16.0D, 13.0D);
    private static final VoxelShape PART_LOWER_Z = Block.makeCuboidShape(4.0D, 4.0D, 3.0D, 12.0D, 5.0D, 13.0D);
    private static final VoxelShape PART_MID_Z = Block.makeCuboidShape(6.0D, 5.0D, 4.0D, 10.0D, 10.0D, 12.0D);
    private static final VoxelShape PART_UPPER_Z = Block.makeCuboidShape(3.0D, 10.0D, 0.0D, 13.0D, 16.0D, 16.0D);
    private static final VoxelShape X_AXIS_AABB = VoxelShapes.or(PART_BASE, PART_LOWER_X, PART_MID_X, PART_UPPER_X);
    private static final VoxelShape Z_AXIS_AABB = VoxelShapes.or(PART_BASE, PART_LOWER_Z, PART_MID_Z, PART_UPPER_Z);

    public BlockArcaneAnvil() {
        super(Properties.create(Material.IRON).notSolid());
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        super.fillStateContainer(builder);
    }

    //###################################
    // Tile Entity stuff
    //###################################

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new WitchAltarTileEntity();
    }



    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Direction direction = state.get(FACING);
        return direction.getAxis() == Direction.Axis.X ? X_AXIS_AABB : Z_AXIS_AABB;
    }

    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().rotateY());
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }
}
