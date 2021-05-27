package com.dpeter99.arcanerituals.registry.mobblood;

import com.google.common.collect.ImmutableMap;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public class MobBloodManager extends JsonReloadListener {

    private static Map<ResourceLocation,MobBlood> bloods = ImmutableMap.of();

    public MobBloodManager() {
        super(new GsonBuilder().create(), "mob_blood");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {

        bloods.clear();

        objectIn.forEach((key, data) ->{

            ResourceLocation mob = new ResourceLocation(data.getAsJsonObject().get("mob").getAsString());
            int hppv = data.getAsJsonObject().get("hppv").getAsInt();

            register(new MobBlood(mob,hppv));

        });
    }

    public void register(MobBlood m){
        bloods.put(m.getMob(),m);
    }

    public static MobBlood get(ResourceLocation target_name) {
        return bloods.get(target_name);
    }
}