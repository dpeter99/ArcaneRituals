package com.dpeter99.ArcaneRituals.fluid;

import com.dpeter99.ArcaneRituals.ArcaneRituals;
import com.dpeter99.ArcaneRituals.arcaneFuel.ArcaneFuelIngredientFluid;
import com.dpeter99.ArcaneRituals.arcaneFuel.IArcaneFuelFluid;
import com.dpeter99.ArcaneRituals.arcaneFuel.ArcaneFuelIngredient;
import com.google.gson.JsonObject;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Blood extends AdvancedFluid implements IArcaneFuelFluid {

    public static final String UNKNOWN_BLOOD = ArcaneRituals.MODID + ":blood_source:unknown";
    public static final String PLAYER_BLOOD = ArcaneRituals.MODID + ":blood_source:player";

    private static final ResourceLocation texture = ArcaneRituals.location("fluid/blood");

    public Blood() {
        super(()-> Items.AIR, FluidAttributes.builder(texture,texture));
        setRegistryName("blood");
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


    @Override
    public ITextComponent getInfoText(FluidStack stack) {
        String owner = "From: " + getFluidData(stack, new BloodData()).getOwnerName();
        return new StringTextComponent(owner).mergeStyle(TextFormatting.DARK_RED);

    }

    //#####################################
    //   ArcaneFuelFluid
    //#####################################

    @Override
    public boolean match(FluidStack a, FluidStack b) {

        if(a.getFluid().equals(this) && b.getFluid().equals(this)){

            BloodData a_data = getFluidData(a, new BloodData());
            BloodData b_data = getFluidData(b, new BloodData());
        }
        return false;
    }

    @Override
    public ResourceLocation getResourceName() {
        return getRegistryName();
    }

    @Override
    public ArcaneFuelIngredient parseIngredient(JsonObject jsonObject) {
        int amount = jsonObject.get("amount").getAsInt();

        BloodData data = new BloodData();
        data.readFromJson(jsonObject);

        FluidStack stack = new FluidStack(ArcaneFluids.blood,amount);
        setFluidData(stack,data);

        ArcaneFuelIngredient<FluidStack> ingredient = new ArcaneFuelIngredientFluid(stack);

        return ingredient;
    }


    public static class BloodData extends FluidData {

        public String owner;
        public UUID player;

        public BloodData() {
            owner = UNKNOWN_BLOOD;
        }

        public BloodData(String owner) {
            this.owner = owner;
        }

        public BloodData(String owner, UUID player) {
            this.owner = owner;
            if(owner.equals(PLAYER_BLOOD)){
                this.player = player;
            }
        }

        public void readFromJson(JsonObject jsonObject){
            owner = jsonObject.get("owner").getAsString();
            if(jsonObject.has("UUID")){
                player = UUID.fromString(jsonObject.get("UUID").getAsString());
            }
        }

        @Override
        public CompoundNBT writeToNBT() {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putString("owner", owner);
            if(player != null){
                nbt.putUniqueId("UUID",player);
            }
            return nbt;
        }

        @Override
        public void readFromNBT(CompoundNBT nbt) {
            owner = nbt.getString("owner");
            if(nbt.contains("UUIDMost")){
                player = nbt.getUniqueId("UUID");
            }
        }

        public String getOwnerName(){
            if(owner.equals(UNKNOWN_BLOOD)){
                return "Mistery";
            }
            else if(owner.equals(PLAYER_BLOOD)){
                if(player != null) {
                    String name = UsernameCache.getLastKnownUsername(player);
                    return name;
                }
                return "Player unknown";
            }
            else{
                String name = ForgeRegistries.ENTITIES.getValue(ResourceLocation.tryCreate(owner)).getName().getString();
                return name;
            }
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
