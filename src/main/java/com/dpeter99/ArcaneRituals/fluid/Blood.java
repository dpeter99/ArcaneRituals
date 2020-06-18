package com.dpeter99.ArcaneRituals.fluid;

import com.dpeter99.ArcaneRituals.util.ArcaneRitualsResourceLocation;
import net.minecraft.fluid.EmptyFluid;
import net.minecraft.util.ResourceLocation;
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
        return com.dpeter99.ArcaneRituals.arcane_fluid.Blood.builder(texture, texture)
                //.translationKey(TranslationHelper.getFluidKey("liquid_crystal"))

                .build(this);
    }




}
