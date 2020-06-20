package com.dpeter99.ArcaneRituals.fluid;

import com.dpeter99.ArcaneRituals.ArcaneRituals;
import com.dpeter99.ArcaneRituals.util.ArcaneRitualsResourceLocation;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Blood extends FluidWithData {

    public static final String UNKNOWN_BLOOD = ArcaneRituals.MODID + ":blood_source:unknown";
    public static final String PLAYER_BLOOD = ArcaneRituals.MODID + ":blood_source:player";

    private static final ResourceLocation texture = new ArcaneRitualsResourceLocation("fluid/blood");

    public Blood() {
        super(()-> Items.AIR, FluidAttributes.builder(texture,texture));
    }

    @Override
    public void setupFluidStack(net.minecraftforge.fluids.FluidStack stack) {
        setFluidData(stack, new Blood.BloodData(UNKNOWN_BLOOD));
    }

    //@Override
    List<FluidStack> mixFluidsInContainer(FluidStack a, FluidStack b, IFluidTank container) {

        //Blood mixes only with itself
        if(a.getFluid() != this && b.getFluid() != this){
            return new ArrayList<FluidStack>();
        }

        FluidStack res = new FluidStack(this,0);

        BloodData data = getFluidData(a,new BloodData(UNKNOWN_BLOOD));
        BloodData other = getFluidData(b,new BloodData(UNKNOWN_BLOOD));

        int free_sapce = container.getCapacity() - container.getFluidAmount();
        int transfer_amount = Math.min(free_sapce,b.getAmount());
        int amount = a.getAmount() + transfer_amount;
        int remaining = b.getAmount() - transfer_amount;

        res.setAmount(amount);

        if(!data.equals(other)){
            data.owner = "Mixed";
        }
        setFluidData(res, data);

        List<FluidStack> results = new ArrayList(1);
        results.add(res);
        if(remaining > 0){
            results.add(new FluidStack(this, remaining));
        }
        return results;
    }



    class BloodData extends FluidData {

        public String owner;
        public UUID player;

        public BloodData(String owner) {
            this.owner = owner;
        }

        @Override
        public CompoundNBT writeToNBT() {
            return null;
        }

        @Override
        public void readFromNBT(CompoundNBT nbt) {

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
