package com.dpeter99.bloodylib;

import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidHelper {

    public static boolean isEmpty(IFluidHandler fluidHandler){
        boolean empty = true;
        for (int i=0; i < fluidHandler.getTanks();i++) {
            empty &= fluidHandler.getFluidInTank(i).isEmpty();
        }
        return empty;
    }

}
