package com.dpeter99.arcanerituals.registry.mobblood;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class MobBlood /*implements IRecipe<RecipeWrapper>*/ {

    public static final String RECIPE_TYPE_NAME = "blood_type";
    public static final ResourceLocation RECIPE_TYPE_ID = ArcaneRituals.location(RECIPE_TYPE_NAME);

    ResourceLocation mob;
    int hppv;

    public MobBlood(ResourceLocation mob, int hppv) {
        this.mob = mob;
        this.hppv = hppv;
    }

    /**
     * Returns the amount of HP needed to fill a single vial
     * @return
     */
    public int getHppv() {
        return hppv;
    }


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