package com.dpeter99.arcanerituals.registry.mobblood;

import com.dpeter99.arcanerituals.registry.ARRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class MobBlood implements IForgeRegistryEntry<MobBlood> {

    //static Map<ResourceLocation,MobBlood> reg = new HashMap<>();



    ResourceLocation mob;
    int hppv;

    public MobBlood(ResourceLocation mob, int hppv) {
        this.mob = mob;
        this.hppv = hppv;
    }



    @Override
    public MobBlood setRegistryName(ResourceLocation name) {
        return null;
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return null;
    }

    @Override
    public Class<MobBlood> getRegistryType() {
        return null;
    }

    /**
     * Returns the amount of HP needed to fill a single vial
     * @return
     */
    public int getHppv() {
        return hppv;
    }

    /*
    public static void register(MobBlood m){
        reg.put(m.getMob(),m);
    }

    public static MobBlood get(ResourceLocation loc){
        return reg.get(loc);
    }
    */

    /**
     * Return the mob this represents.
     * @return
     */
    public ResourceLocation getMob() {
        return mob;
    }

    public void setMob(ResourceLocation mob) {
        this.mob = mob;
    }


}