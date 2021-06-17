package com.dpeter99.bloodylib;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;

public class FluidHelper {

    public static boolean isEmpty(IFluidHandler fluidHandler){
        boolean empty = true;
        for (int i=0; i < fluidHandler.getTanks();i++) {
            empty &= fluidHandler.getFluidInTank(i).isEmpty();
        }
        return empty;
    }

    public static boolean isFull(IFluidHandler fluidHandler){

        boolean full = true;
        for (int i=0; i < fluidHandler.getTanks();i++) {
            full &= fluidHandler.getFluidInTank(i).getAmount() == fluidHandler.getTankCapacity(i);
        }
        return full;
    }

    public static boolean isFluidContainer(@Nonnull ItemStack stack) {
        return stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent();
    }

}
