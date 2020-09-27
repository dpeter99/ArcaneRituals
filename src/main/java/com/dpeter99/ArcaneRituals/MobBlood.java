package com.dpeter99.ArcaneRituals;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobBlood {

    static Map<ResourceLocation,MobBlood> reg = new HashMap<>();



    ResourceLocation mob;
    int hppv;

    public MobBlood(ResourceLocation mob, int hppv) {
        this.mob = mob;
        this.hppv = hppv;
    }

    public int getHppv() {
        return hppv;
    }

    public static void register(MobBlood m){
        reg.put(m.getMob(),m);
    }

    public static MobBlood get(ResourceLocation loc){
        return reg.get(loc);
    }

    public ResourceLocation getMob() {
        return mob;
    }

    public void setMob(ResourceLocation mob) {
        this.mob = mob;
    }
}
