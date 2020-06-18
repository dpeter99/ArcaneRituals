package com.dpeter99.ArcaneRituals.arcane_fluid;

public class FluidStack {

    int amount = 0;
    Fluid fluid;

    Fluid.FluidData data;

    public FluidStack() {
        this.amount = 0;
        this.fluid = null;
    }

    public FluidStack(Fluid fluid){
        this.fluid = fluid;
    }

    public FluidStack(FluidStack resource, int amount) {
        this.fluid = resource.fluid;
        this.amount = amount;
    }

    public void setFluidData(Fluid.FluidData data){
        this.data = data;
    }

    public Fluid.FluidData getFluidData(){
        return this.data;
    }


    void fillFrom(FluidStack other){

    }

    public boolean isEmpty() {
        return fluid == null;
    }

    public int getAmount() {
        return amount;
    }
}
