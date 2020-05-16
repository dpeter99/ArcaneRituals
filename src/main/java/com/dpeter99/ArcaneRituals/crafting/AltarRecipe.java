package com.dpeter99.ArcaneRituals.crafting;

import com.dpeter99.ArcaneRituals.Arcanerituals;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
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

    public List<Ingredient> ingredients;
    public ItemStack result;

    public AltarRecipe(ResourceLocation id, String group, List<Ingredient> ingredients, ItemStack result) {
        this.id = id;
        this.group = group;
        this.ingredients = ingredients;
        this.result = result;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     *
     * @param inv
     * @param worldIn
     */
    @Override
    public boolean matches(AltarContext inv, World worldIn) {

        boolean found[] = new boolean[ingredients.size()];
        int found_c =0;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            for (int j = 0; j < ingredients.size();j++) {
                if(!found[j] && ingredients.get(j).test(inv.getStackInSlot(i))){
                    found[j] =  true;
                    found_c ++;
                }
            }
        }
        return found_c == ingredients.size();
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
        return null;
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


            JsonArray ingredientsArray = JSONUtils.getJsonArray(json, "ingredient");
            List<Ingredient> ingredient = new ArrayList<>();
            for (JsonElement e : ingredientsArray) {
                ingredient.add(Ingredient.deserialize(e));
            }

            String s1 = JSONUtils.getString(json, "result");
            ResourceLocation resourcelocation = new ResourceLocation(s1);
            ItemStack itemstack = new ItemStack(Optional.ofNullable(ForgeRegistries.ITEMS.getValue(resourcelocation)).orElseThrow(() -> new IllegalStateException("Item: " + s1 + " does not exist")));

            return new AltarRecipe(recipeId, group, ingredient, itemstack);
        }

        @Override
        public AltarRecipe read(ResourceLocation recipeId, PacketBuffer buffer)
        {

            String group = buffer.readString(32767);
            ItemStack itemstack = buffer.readItemStack();

            int i = buffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(i, Ingredient.EMPTY);

            for(int j = 0; j < ingredients.size(); ++j) {
                ingredients.set(j, Ingredient.read(buffer));
            }

            return new AltarRecipe(recipeId, group, ingredients, itemstack);
        }

        @Override
        public void write(PacketBuffer buffer, AltarRecipe recipe)
        {
            buffer.writeString(recipe.group);
            buffer.writeVarInt(recipe.ingredients.size());
            for(Ingredient ingredient : recipe.ingredients) {
                ingredient.write(buffer);
            }

            buffer.writeItemStack(recipe.result);
        }
    }
}
