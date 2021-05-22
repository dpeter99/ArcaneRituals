package com.dpeter99.ArcaneRituals.crafting;

import com.dpeter99.ArcaneRituals.ArcaneRituals;
import com.dpeter99.ArcaneRituals.arcaneFuel.ArcaneFuelIngredient;
import com.dpeter99.ArcaneRituals.arcaneFuel.instance.ArcaneFuelFluidInstance;
import com.dpeter99.ArcaneRituals.arcaneFuel.instance.ArcaneFuelInstance;
import com.google.gson.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AltarRecipe implements IRecipe<AltarContext> {

    public static final String RECIPE_TYPE_NAME = "altar";

    @ObjectHolder(ArcaneRituals.MODID+":"+RECIPE_TYPE_NAME)
    public static IRecipeSerializer<?> SERIALIZER = null;

    public static final ResourceLocation RECIPE_TYPE_ID = ArcaneRituals.location(RECIPE_TYPE_NAME);
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
    public Ingredient center;
    public ItemStack result;

    public ArcaneFuelIngredient fuel;

    public int work_amount;

    public AltarRecipe(ResourceLocation id, String group, List<Ingredient> ingredients, Ingredient center, ArcaneFuelIngredient fuel, ItemStack result, String altar_type, int work) {
        this.id = id;
        this.group = group;
        this.ingredients = ingredients;
        this.center = center;
        this.result = result;
        this.fuel = fuel;
        this.altar_type = altar_type;
        this.work_amount = work;
    }


    @Override
    public boolean matches(AltarContext inv, World worldIn) {
        if(inv.altar_type.equals(this.altar_type)){

                //List for all the items that we need to match
                List<ItemStack> items = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    items.add(inv.getStackInSlot(i));
                }
                int found = 0;
                for (Ingredient ing : ingredients) {
                    for (ItemStack item: items) {
                        ing.test(item);
                        items.remove(item);
                        found++;
                        break;
                    }
                }

                boolean matches =  (found == 4)
                                && (items.size() == 0);

                matches = matches && center.test(inv.getStackInSlot(4));

                matches = matches && fuel.test(inv.getFuel());

                return matches;
            }

        return false;

    }

    @Override
    public ItemStack getCraftingResult(AltarContext inv) {
        return result;
    }

    public ItemStack getCenter(){
        return center.getMatchingStacks()[0];
    }

    @Override
    public boolean canFit(int width, int height) {
        //TODO use this to check for the size of the altar
        return true;
    }


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

            ArcaneFuelIngredient<?> fuel = ArcaneFuelIngredient.deserialize(json.getAsJsonObject("fuel"));

            String altar_type = JSONUtils.getString(json,"altar_type","");

            int work = JSONUtils.getInt(json,"work_amount",0);

            JsonObject ingredientsArray = JSONUtils.getJsonObject(json, "ingredients");
            List<Ingredient> ingredient = new ArrayList<>();
            ingredient.add(Ingredient.deserialize(ingredientsArray.getAsJsonObject("0")));
            ingredient.add(Ingredient.deserialize(ingredientsArray.getAsJsonObject("1")));
            ingredient.add(Ingredient.deserialize(ingredientsArray.getAsJsonObject("2")));
            ingredient.add(Ingredient.deserialize(ingredientsArray.getAsJsonObject("3")));

            Ingredient center = Ingredient.deserialize(ingredientsArray.getAsJsonObject("center"));

            String s1 = JSONUtils.getString(json, "result");
            ResourceLocation resourcelocation = new ResourceLocation(s1);
            ItemStack itemstack = new ItemStack(Optional.ofNullable(ForgeRegistries.ITEMS.getValue(resourcelocation)).orElseThrow(() -> new IllegalStateException("Item: " + s1 + " does not exist")));

            return new AltarRecipe(recipeId, group, ingredient,center,fuel, itemstack, altar_type, work);
        }

        @Override
        public AltarRecipe read(ResourceLocation recipeId, PacketBuffer buffer)
        {

            String group = buffer.readString(32767);
            int fuel_amount = buffer.readVarInt();

            ArcaneFuelIngredient<?> fuel = ArcaneFuelIngredient.read(buffer);

            String altar_type = buffer.readString();
            int work = buffer.readVarInt();

            ItemStack itemstack = buffer.readItemStack();
            int i = buffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(i, Ingredient.EMPTY);

            for(int j = 0; j < ingredients.size(); ++j) {
                ingredients.set(j, Ingredient.read(buffer));
            }
            Ingredient center = Ingredient.read(buffer);

            return new AltarRecipe(recipeId, group, ingredients,center, fuel, itemstack, altar_type, work);
        }

        @Override
        public void write(PacketBuffer buffer, AltarRecipe recipe)
        {
            buffer.writeString(recipe.group);

            recipe.fuel.write(buffer);

            buffer.writeString(recipe.altar_type);
            buffer.writeVarInt(recipe.work_amount);


            buffer.writeVarInt(recipe.ingredients.size());
            for(Ingredient ingredient : recipe.ingredients) {
                ingredient.write(buffer);
            }
            recipe.center.write(buffer);

            buffer.writeItemStack(recipe.result);
        }
    }
}
