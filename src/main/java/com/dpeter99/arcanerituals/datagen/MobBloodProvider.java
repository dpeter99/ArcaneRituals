package com.dpeter99.arcanerituals.datagen;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dpeter99.arcanerituals.ArcaneRituals;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;

public class MobBloodProvider implements IDataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger LOGGER = LogManager.getLogger();

    private Map<String, JsonObject> toSerialize = new HashMap<>();
    private DataGenerator generator;

    public MobBloodProvider(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(DirectoryCache cache) throws IOException {
        Path folder = generator.getOutputFolder();
        start();

        toSerialize.forEach((name, json) -> {
            Path path = folder.resolve("data/" + ArcaneRituals.MODID + "/mob_blood/" + name + ".json");
            try {
                IDataProvider.save(GSON, cache, json, path);
            } catch (IOException e) {
                LOGGER.error("Couldn't save mob blood {}", path, e);
            }
        });
    }

    private void start() {
        addMobBlood(EntityType.SHEEP, 1);
        addMobBlood(EntityType.IRON_GOLEM, 15);
    }

    private void addMobBlood(EntityType<?> type, int hppv) {
        ResourceLocation name = type.getRegistryName();
        JsonObject json = new JsonObject();
        json.addProperty("mob", name.toString());
        json.addProperty("hppv", hppv);
        toSerialize.put(name.getPath(), json);
    }

    @Override
    public String getName() {
        return "Mob Blood: " + ArcaneRituals.MODID;
    }

}
               