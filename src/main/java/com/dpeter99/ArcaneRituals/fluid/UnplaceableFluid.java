package com.dpeter99.ArcaneRituals.fluid;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.fluids.FluidAttributes;

import java.util.function.Supplier;

public class UnplaceableFluid extends Fluid {

  private final Supplier<? extends Item> bucket;
  private final FluidAttributes.Builder builder;

  public UnplaceableFluid(Supplier<? extends Item> bucket, FluidAttributes.Builder builder) {
    this.bucket = bucket;
    this.builder = builder;
  }

  @Override
  public Item getFilledBucket() {
    return bucket.get();
  }

  @Override
  protected boolean canDisplace(IFluidState p_215665_1_, IBlockReader p_215665_2_, BlockPos p_215665_3_, Fluid p_215665_4_, Direction p_215665_5_) {
    return false;
  }

  @Override
  public int getTickRate(IWorldReader p_205569_1_) {
    return 5;
  }

  @Override
  protected float getExplosionResistance() {
    return 100;
  }

  @Override
  protected BlockState getBlockState(IFluidState state) {
    return Blocks.AIR.getDefaultState();
  }

  @Override
  protected FluidAttributes createAttributes()
  {
    return builder.build(this);
  }

  /* Required methods */

  @Override
  protected Vec3d getFlow(IBlockReader p_215663_1_, BlockPos p_215663_2_, IFluidState p_215663_3_) {
    return new Vec3d(0, 0, 0);
  }

  @Override
  public boolean isSource(IFluidState state) {
    return false;
  }

  @Override
  public float getHeight(IFluidState p_223407_1_) {
    return 1;
  }

  @Override
  public float getActualHeight(IFluidState p_215662_1_, IBlockReader p_215662_2_, BlockPos p_215662_3_) {
    return 1;
  }

  @Override
  public int getLevel(IFluidState p_207192_1_) {
    return 0;
  }

  @Override
  public VoxelShape func_215664_b(IFluidState p_215664_1_, IBlockReader p_215664_2_, BlockPos p_215664_3_) {
    return VoxelShapes.fullCube();
  }
}