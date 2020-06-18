package com.dpeter99.ArcaneRituals.arcane_fluid;

import net.minecraft.nbt.CompoundNBT;

import java.util.HashMap;
import java.util.UUID;

public class Blood extends Fluid {

    public Blood() {
    }


    @Override
    void setupFluidStack(FluidStack s) {
        s.setFluidData(new BloodData("unknown"));
    }

    @Override
    int mixFluidsInContainer(FluidStack a, FluidStack b, int availableSpace) {

        //Blood mixes only with itself
        if(a.fluid != this && b.fluid != this){
            return 0;
        }

        FluidStack res = new FluidStack(this);

        BloodData data = ((BloodData)a.getFluidData());
        a.amount += Math.min(b.amount,availableSpace);
        if(!data.equals(b.getFluidData())){
            data.owner = "Mixed";
        }

        return ;
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
