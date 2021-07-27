package com.dpeter99.bloodylib.fluid;

import net.minecraft.world.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Supplier;

/**
 * Adds functions for storing data on the fluid
 */
public abstract class AdvancedFluid extends UnplaceableFluid  {

    public AdvancedFluid(Supplier<? extends Item> bucket, FluidAttributes.Builder builder) {
        super(bucket, builder);
    }

    public Component getInfoText(FluidStack stack){
        return new TextComponent("");
    }

    public void setFluidData(FluidStack stack, FluidData data){
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.put("data", data.writeToNBT());
    }

    public <T extends FluidData> T getFluidData(FluidStack stack, T data){
        CompoundTag nbt =stack.getTag();
        if(nbt != null && nbt.contains("data")){
            data.readFromNBT(nbt.getCompound("data"));
            return data;
        }
        return null;

    }

    public abstract void setupFluidStack(FluidStack stack);

    public static FluidStack makeFluidStack(AdvancedFluid fluid, int amount, FluidData data){

            FluidStack stack = new FluidStack(fluid, amount);
             fluid.setFluidData(stack, data);

            return stack;

    }
}