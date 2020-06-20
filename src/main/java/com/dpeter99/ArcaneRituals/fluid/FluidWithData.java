package com.dpeter99.ArcaneRituals.fluid;

import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Supplier;

public abstract class FluidWithData extends UnplaceableFluid {


    public FluidWithData(Supplier<? extends Item> bucket, FluidAttributes.Builder builder) {
        super(bucket, builder);
    }

    public void setFluidData(FluidStack stack, FluidData data){
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.put("data", data.writeToNBT());
    }

    public <T extends FluidData> T getFluidData(FluidStack stack, T data){
        CompoundNBT nbt =stack.getTag();
        if(nbt != null && nbt.contains("data")){
            data.readFromNBT(nbt.getCompound("data"));
            return data;
        }
        return null;

    }

    public abstract void setupFluidStack(FluidStack stack);

}
