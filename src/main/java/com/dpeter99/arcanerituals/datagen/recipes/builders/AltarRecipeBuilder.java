package com.dpeter99.arcanerituals.datagen.recipes.builders;

import com.dpeter99.arcanerituals.ArcaneRituals;
import com.dpeter99.arcanerituals.crafting.ArcaneFuel;
import com.dpeter99.arcanerituals.registry.ARRegistry;
import com.dpeter99.arcanerituals.tileentities.AltarTileEntity;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class AltarRecipeBuilder {


    private ResourceLocation altarType;
    private int workAmount;
    private final Item result;
    private final int count;

    ArcaneFuel fuel;

    private List<Ingredient> ingredients = new ArrayList();

    private Ingredient center;

    public AltarRecipeBuilder(ItemLike result, int count, ResourceLocation altarType,int workAmount) {
        this.result = result.asItem();
        this.count = count;
        this.altarType = altarType;
        this.workAmount = workAmount;
    }

    public static AltarRecipeBuilder blood(ItemLike p_200468_0_, int p_200468_1_,int workAmount) {

        return new AltarRecipeBuilder(p_200468_0_, p_200468_1_, AltarTileEntity.BLOOD_ALTAR_TYPE,workAmount);
    }


    public AltarRecipeBuilder fuel(ArcaneFuel fuel){
        this.fuel = fuel;
        return this;
    }

    public AltarRecipeBuilder fuel(FluidStack fluid){
        this.fuel = new ArcaneFuel(fluid);
        return this;
    }

    public AltarRecipeBuilder addIngredient(Ingredient ing) {
        this.ingredients.add(ing);
        return this;
    }

    public AltarRecipeBuilder addIngredient(ItemLike ing){
        this.ingredients.add(Ingredient.of(ing));
        return this;
    }

    public AltarRecipeBuilder setCenterIngredient(Ingredient ing) {
        this.center = ing;
        return this;
    }

    public AltarRecipeBuilder setCenterIngredient(ItemLike ing){
        this.center = Ingredient.of(ing);
        return this;
    }


    public void save(Consumer<FinishedRecipe> p_200464_1_) {
        this.save(p_200464_1_, ArcaneRituals.location("altar/"+result.getRegistryName().getPath()));
    }


    public void save(Consumer<FinishedRecipe> consumer, ResourceLocation p_200467_2_) {
        //this.ensureValid(p_200467_2_);
        //this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_200467_2_)).rewards(net.minecraft.advancements.AdvancementRewards.Builder.recipe(p_200467_2_)).requirements(IRequirementsStrategy.OR);
        consumer.accept(new AltarRecipeBuilder.Result(p_200467_2_, this.result, this.count, this.fuel, altarType, ingredients, center, workAmount));
    }


    public class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item result;
        private final int count;
        private final ArcaneFuel fuel;
        private final ResourceLocation altarType;

        private final List<Ingredient> ingredients;
        private final Ingredient center;

        private int workAmount;

        public Result(ResourceLocation location, Item p_i48271_3_, int p_i48271_4_, ArcaneFuel fuel, ResourceLocation altarType, List<Ingredient> ingredients, Ingredient center, int workAmount) {
            this.id = location;
            this.result = p_i48271_3_;
            this.count = p_i48271_4_;
            this.fuel = fuel;
            this.altarType = altarType;
            this.ingredients = ingredients;
            this.center = center;
            this.workAmount = workAmount;
        }

        public void serializeRecipeData(JsonObject inJson) {

            JsonObject result = new JsonObject();
            result.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
            if (this.count > 1) {
                result.addProperty("count", this.count);
            }
            inJson.add("result", result);

            JsonObject fuel = new JsonObject();
            fuel.addProperty("type",this.fuel.getId().toString());
            fuel.addProperty("amount",this.fuel.getAmount());

            JsonElement fuel_nbt = NbtOps.INSTANCE.convertTo(JsonOps.INSTANCE, this.fuel.getNbt());
            fuel.add("nbt", fuel_nbt);

            inJson.add("fuel", fuel);

            inJson.addProperty("altar_type", altarType.toString());

            JsonObject ing = new JsonObject();
            for (int i = 0; i < ingredients.size(); i++) {
                ing.add(String.valueOf(i),ingredients.get(i).toJson());
            }
            ing.add("center", center.toJson());

            inJson.add("ingredients", ing);

            inJson.addProperty("work_amount", workAmount);
        }

        public RecipeSerializer<?> getType() {
            return ARRegistry.ALTAR_RECIPE_SERIALIZER.get();
        }

        public ResourceLocation getId() {
            return this.id;
        }




        //TODO: later
        @Nullable
        public JsonObject serializeAdvancement() {
            return null;// this.advancement.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return null;// this.advancementId;
        }
    }

}
