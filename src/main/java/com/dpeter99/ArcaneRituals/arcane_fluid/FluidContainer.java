package com.dpeter99.ArcaneRituals.arcane_fluid;

import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidContainer {

    int capacity = 5000;
    boolean allowMixing = true;

    FluidStack fluid = new FluidStack();

    public int getCapacity(){
        return capacity;
    }

    public int getFreeCapacity(){
        return capacity - fluid.amount;
    }

    public boolean multiFluidContainer(){
        return false;
    }

    /**
     * Fills fluid into internal tanks from the given FluidStack, distribution is left entirely to the IFluidHandler.
     *
     * @param resource FluidStack representing the Fluid and maximum amount of fluid to be filled.
     * @param action   If SIMULATE, fill will only be simulated.
     * @return Amount of resource that was (or would have been, if simulated) filled.
     */
    public int fillFrom(FluidStack resource, IFluidHandler.FluidAction action)
    {
        //FluidStack A + B
        //If container is empty
        //  - Than the new fluid fills it
        //Mix / no Mix
        //Mix:
        //  - Ask itemStack for what the mix is
        //    - If null than no mixing
        //  -

        if (resource.isEmpty()) {
            return 0;
        }

        FluidStack new_stack = fluid;

        if(allowMixing){
            new_stack = fluid.mixFrom(resource,capacity,action);
        }





        int transfer = 0;
        int space = capacity - fluid.amount;
        if(space > 0){
            transfer = Math.min(space, resource.getAmount());
        }


        if (action.simulate())
        {
           return transfer;
        }

        FluidStack transfer_stack = new FluidStack(resource,transfer);

        if (fluid.isEmpty())
        {
            fluid = transfer_stack;
            return transfer;
        }




        return transfer;
    }

}
