package com.dpeter99.arcanerituals.registry.mobblood;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public class MobBloodLoader extends JsonReloadListener {

    public MobBloodLoader() {
        super(new GsonBuilder().create(), "mob_blood");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, IResourceManager resourceManagerIn, IProfiler profilerIn) {

        objectIn.forEach((key, data) ->{

            ResourceLocation mob = new ResourceLocation(data.getAsJsonObject().get("mob").getAsString());
            int hppv = data.getAsJsonObject().get("hppv").getAsInt();

            MobBlood.register(new MobBlood(mob,hppv));

        });
    }
}