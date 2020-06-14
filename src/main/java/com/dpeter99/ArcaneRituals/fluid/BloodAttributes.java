package com.dpeter99.ArcaneRituals.fluid;

import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;

import java.util.function.BiFunction;

public class BloodAttributes extends FluidAttributes {
    protected BloodAttributes(Builder builder, Fluid fluid) {
        super(builder, fluid);
    }

    public static class Builder extends FluidAttributes.Builder{

        protected Builder(ResourceLocation stillTexture, ResourceLocation flowingTexture, BiFunction<FluidAttributes.Builder, Fluid, FluidAttributes> factory) {
            super(stillTexture, flowingTexture, factory);
        }
    }

}
