package com.dpeter99.ArcaneRituals.crafting;

import com.dpeter99.ArcaneRituals.Arcanerituals;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.fixes.WolfCollarColor;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AltarRecipe implements IRecipe<AltarContext> {

    public static final String RECIPE_TYPE_NAME = "altar";

    @ObjectHolder(Arcanerituals.MODID+":"+RECIPE_TYPE_NAME)
    public static IRecipeSerializer<?> SERIALIZER = null;

    public static final ResourceLocation RECIPE_TYPE_ID = Arcanerituals.location(RECIPE_TYPE_NAME);
    public static IRecipeType<AltarRecipe> ALTAR = Registry.register(Registry.RECIPE_TYPE, RECIPE_TYPE_ID, new IRecipeType<AltarRecipe>()
    {
        @Override
        public String toString()
        {
            return RECIPE_TYPE_ID.toString();
        }
    });

    public static Optional<AltarRecipe> getRecipe(World world, AltarContext ctx)
    {
        return world.getRecipeManager().getRecipe(ALTAR, ctx, world);
    }


    private final ResourceLocation id;
    private final String group;

    public String altar_type;
    public List<Ingredient> ingredients;
    public ItemStack result;
    public int fuel_amount;

    public AltarRecipe(ResourceLocation id, String group, List<Ingredient> ingredients, ItemStack result, int fuel_amount, String altar_type) {
        this.id = id;
        this.group = group;
        this.ingredients = ingredients;
        this.result = result;
        this.fuel_amount = fuel_amount;
        this.altar_type = altar_type;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     *
     * @param inv
     * @param worldIn
     */
    @Override
    public boolean matches(AltarContext inv, World worldIn) {
        if(inv.altar_type.equals(this.altar_type)){
            if(inv.fuel_amount >= this.fuel_amount){
/*
                boolean found[] = new boolean[ingredients.size()];
                int found_c =0;

                for (int j = 0; j < ingredients.size();j++) {
                    if(!found[j] && ingredients.get(j).test(inv.getStackInSlot(i))){
                        found[j] =  true;
                        found_c ++;
                    }
                }


                return found_c == ingredients.size();

 */

                RecipeItemHelper recipeitemhelper = new RecipeItemHelper();
                java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
                int i = 0;

                for(int j = 0; j < inv.getSizeInventory(); ++j) {
                    ItemStack itemstack = inv.getStackInSlot(j);
                    if (!itemstack.isEmpty()) {
                        ++i;
                        inputs.add(itemstack);
                    }
                }

                return i == this.ingredients.size() && net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs,  this.ingredients) != null;


            }
        }
        return false;

    }

    /**
     * Returns an Item that is the result of this recipe
     *
     * @param inv
     */
    @Override
    public ItemStack getCraftingResult(AltarContext inv) {
        return result;
    }


    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     *
     * @param width
     * @param height
     */
    @Override
    public boolean canFit(int width, int height) {
        //TODO use this to check for the size of the altar
        return true;
    }

    /**
     * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
     * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
     */
    @Override
    public ItemStack getRecipeOutput() {
        return result;
    }

    @Override
    public ResourceLocation getId()
    {
        return id;
    }

    @Override
    public String getGroup()
    {
        return group;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return ALTAR;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
            implements IRecipeSerializer<AltarRecipe>
    {
        @Override
        public AltarRecipe read(ResourceLocation recipeId, JsonObject json)
        {
            String group = JSONUtils.getString(json, "group", "");
            int fuel = JSONUtils.getInt(json,"fuel_amount",0);
            String altar_type = JSONUtils.getString(json,"altar_type","");

            JsonArray ingredientsArray = JSONUtils.getJsonArray(json, "ingredient");
            List<Ingredient> ingredient = new ArrayList<>();
            for (JsonElement e : ingredientsArray) {
                ingredient.add(Ingredient.deserialize(e));
            }

            String s1 = JSONUtils.getString(json, "result");
            ResourceLocation resourcelocation = new ResourceLocation(s1);
            ItemStack itemstack = new ItemStack(Optional.ofNullable(ForgeRegistries.ITEMS.getValue(resourcelocation)).orElseThrow(() -> new IllegalStateException("Item: " + s1 + " does not exist")));

            return new AltarRecipe(recipeId, group, ingredient, itemstack, fuel, altar_type);
        }

        @Override
        public AltarRecipe read(ResourceLocation recipeId, PacketBuffer buffer)
        {

            String group = buffer.readString(32767);
            int fuel_amount = buffer.readVarInt();
            String altar_type = buffer.readString();

            ItemStack itemstack = buffer.readItemStack();
            int i = buffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(i, Ingredient.EMPTY);

            for(int j = 0; j < ingredients.size(); ++j) {
                ingredients.set(j, Ingredient.read(buffer));
            }

            return new AltarRecipe(recipeId, group, ingredients, itemstack, fuel_amount, altar_type);
        }

        @Override
        public void write(PacketBuffer buffer, AltarRecipe recipe)
        {
            buffer.writeString(recipe.group);
            buffer.writeVarInt(recipe.fuel_amount);
            buffer.writeString(recipe.altar_type);


            buffer.writeVarInt(recipe.ingredients.size());
            for(Ingredient ingredient : recipe.ingredients) {
                ingredient.write(buffer);
            }

            buffer.writeItemStack(recipe.result);
        }
    }
}
