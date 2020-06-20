package com.dpeter99.ArcaneRituals.arcane_fluid;

import net.minecraft.nbt.CompoundNBT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Blood extends Fluid {

    public Blood() {
    }


    @Override
    void setupFluidStack(FluidStack s) {
        s.setFluidData(new BloodData("unknown"));
    }

    @Override
    List<FluidStack> mixFluidsInContainer(FluidStack a, FluidStack b, FluidContainer container) {

        //Blood mixes only with itself
        if(a.fluid != this && b.fluid != this){
            return new ArrayList<FluidStack>();
        }

        FluidStack res = new FluidStack(this);

        BloodData data = ((BloodData)a.getFluidData());
        a.amount += Math.min(b.amount,container.getFreeCapacity());
        if(!data.equals(b.getFluidData())){
            data.owner = "Mixed";
        }

        return 0;
    }

    class BloodData extends Fluid.FluidData{

        public String owner;

        public BloodData(String owner) {
            this.owner = owner;
        }

        @Override
        public CompoundNBT writeToNBT() {
            return null;
        }

        @Override
        public void readFromNBT() {

        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof BloodData){
                return owner.equals(((BloodData) obj).owner);
            }
            return false;
        }
    }
}
