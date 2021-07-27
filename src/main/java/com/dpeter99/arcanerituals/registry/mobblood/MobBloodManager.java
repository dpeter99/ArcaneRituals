package com.dpeter99.arcanerituals.registry.mobblood;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class MobBloodManager extends SimpleJsonResourceReloadListener {

    private static Map<ResourceLocation,MobBlood> bloods = new HashMap<>();

    public MobBloodManager() {
        super(new GsonBuilder().create(), "mob_blood");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {

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