package com.dpeter99.ArcaneRituals.fluid;

import com.dpeter99.ArcaneRituals.util.ArcaneRitualsResourceLocation;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.EmptyFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.fluids.FluidAttributes;

import javax.annotation.Nonnull;

public class Blood extends EmptyFluid {

    private static final ResourceLocation texture = new ArcaneRitualsResourceLocation("fluid/blood");

    public Blood() {
        setRegistryName(new ArcaneRitualsResourceLocation("blood"));
    }

    @Nonnull
    @Override
    protected FluidAttributes createAttributes() {
        return FluidAttributes.builder(texture, texture)
                //.translationKey(TranslationHelper.getFluidKey("liquid_crystal"))
                .build(this);
    }

}
