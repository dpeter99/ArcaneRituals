package com.dpeter99.ArcaneRituals.fluid;

import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;

import java.util.function.Supplier;

public abstract class AdvancedFluid extends UnplaceableFluid {


    public AdvancedFluid(Supplier<? extends Item> bucket, FluidAttributes.Builder builder) {
        super(bucket, builder);
    }

    public ITextComponent getInfoText(FluidStack stack){
        return new StringTextComponent("");
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

    public FluidStack makeFluidStack(int amount, FluidData data){
        FluidStack stack = new FluidStack(this,amount);
        setFluidData(stack, data);

        return stack;
    }
}
