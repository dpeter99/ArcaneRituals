package com.dpeter99.arcanerituals.fluids;

import com.dpeter99.arcanerituals.ArcaneRituals;
import com.dpeter99.arcanerituals.registry.ARRegistry;
import com.dpeter99.bloodylib.fluid.AdvancedFluid;
import com.dpeter99.bloodylib.fluid.FluidData;
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

public class Blood extends AdvancedFluid /*implements IArcaneFuelFluid*/ {

    public static final ResourceLocation UNKNOWN_BLOOD =  ArcaneRituals.location ("blood_source/unknown");
    public static final ResourceLocation PLAYER_BLOOD = ArcaneRituals.location("blood_source/player");

    public static final ResourceLocation MIXED_BLOOD = ArcaneRituals.location("blood_source/mixed");

    private static final ResourceLocation texture = ArcaneRituals.location("fluid/blood");

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
            data.owner = MIXED_BLOOD;
        }
        setFluidData(res, data);

        List<FluidStack> results = new ArrayList(1);
        results.add(res);
        if(remaining > 0){
            results.add(new FluidStack(this, remaining));
        }
        return results;
    }

    public static FluidStack makeFluidStack(int amount, BloodData data){
        return makeFluidStack(ARRegistry.BLOOD.get(), amount,data);
    }

    public static FluidStack makeFluidStack(int amount, ResourceLocation loc){
        return makeFluidStack(ARRegistry.BLOOD.get(), amount,new BloodData(loc));
    }

    @Override
    public ITextComponent getInfoText(FluidStack stack) {
        String owner = "From: " + getFluidData(stack, new BloodData()).getOwnerName();
        return new StringTextComponent(owner).withStyle(TextFormatting.DARK_RED);

    }


    public static class BloodData extends FluidData {

        public ResourceLocation owner;
        public UUID player;

        public BloodData() {
            owner = UNKNOWN_BLOOD;
        }

        public BloodData(ResourceLocation owner) {
            this.owner = owner;
        }

        public BloodData(ResourceLocation owner, UUID player) {
            this.owner = owner;
            if(owner.equals(PLAYER_BLOOD)){
                this.player = player;
            }
        }

        public void readFromJson(JsonObject jsonObject){
            owner = ResourceLocation.tryParse(jsonObject.get("owner").getAsString());
            if(jsonObject.has("UUID")){
                player = UUID.fromString(jsonObject.get("UUID").getAsString());
            }
        }

        @Override
        public CompoundNBT writeToNBT() {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putString("owner", owner.toString());
            if(player != null){
                nbt.putUUID("UUID",player);
            }
            return nbt;
        }

        @Override
        public void readFromNBT(CompoundNBT nbt) {
            owner = ResourceLocation.tryParse(nbt.getString("owner"));
            if(owner.equals(Blood.PLAYER_BLOOD) && nbt.hasUUID("UUID")){
                //TODO: this happens... probably some bad syncing issue
                //ArcaneRituals.LOGGER.warn("No UUID for the player blood");
                player = nbt.getUUID("UUID");
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
                String name = ForgeRegistries.ENTITIES.getValue(owner).getRegistryName().toString();
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
