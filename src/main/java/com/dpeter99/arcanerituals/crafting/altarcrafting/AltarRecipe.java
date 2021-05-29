package com.dpeter99.arcanerituals.crafting.altarcrafting;

import com.dpeter99.arcanerituals.ArcaneRituals;
import com.dpeter99.arcanerituals.crafting.ArcaneFuel;
import com.dpeter99.arcanerituals.registry.ARRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class AltarRecipe implements IRecipe<AltarContext> {

    public static final String RECIPE_TYPE_NAME = "altar";
    public static final ResourceLocation RECIPE_TYPE_ID = ArcaneRituals.location(RECIPE_TYPE_NAME);

    public static final IRecipeType<AltarRecipe> ALTAR_RECIPE_TYPE = IRecipeType.register(RECIPE_TYPE_ID.toString());

    public final ResourceLocation id;
    public final String group;

    public final List<Ingredient> ingredients;
    public final Ingredient center;

    public final ArcaneFuel fuel;

    public final ItemStack result;

    public final String altar_type;
    public final int work_amount;

    public AltarRecipe(ResourceLocation id, String group, List<Ingredient> ingredient, Ingredient center, ArcaneFuel fuel, ItemStack result, String altar_type, int work_amount) {
        this.id = id;
        this.group = group;
        this.ingredients = ingredient;
        this.center = center;
        this.fuel = fuel;
        this.result = result;
        this.altar_type = altar_type;
        this.work_amount = work_amount;
    }


    @Override
    public boolean matches(AltarContext p_77569_1_, World worldIn) {
        return false;
    }

    /**
     *
     * @param p_77572_1_
     * @return
     */
    @Override
    public ItemStack assemble(AltarContext p_77572_1_) {

        return null;
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return true;
    }


    @Override
    public ItemStack getResultItem() {
        return null;
    }

    @Override
    public ResourceLocation getId() {
        return null;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ARRegistry.ALTAR_RECIPE_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ALTAR_RECIPE_TYPE;
    }

}
