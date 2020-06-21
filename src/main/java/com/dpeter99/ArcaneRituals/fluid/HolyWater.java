package com.dpeter99.ArcaneRituals.fluid;

import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Supplier;

public class HolyWater extends AdvancedFluid {

    public HolyWater(Supplier<? extends Item> bucket, FluidAttributes.Builder builder) {
        super(bucket, builder);
    }

    @Override
    public void setupFluidStack(FluidStack stack) {

    }

}
