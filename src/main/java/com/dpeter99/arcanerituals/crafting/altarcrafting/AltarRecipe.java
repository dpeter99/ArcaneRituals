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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public static Optional<AltarRecipe> getRecipe(World world, AltarContext ctx)
    {
        return world.getRecipeManager().getRecipeFor(ALTAR_RECIPE_TYPE, ctx, world);
    }

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
    public boolean matches(AltarContext ctx, World worldIn) {
        if(!ctx.altar_type.equals(altar_type)){
            return false;
        }

        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            items.add(ctx.getItem(i));
        }

        int found = 0;
        for (Ingredient ing : ingredients) {
            for (ItemStack item: items) {
                if(ing.test(item)) {
                    items.remove(item);
                    found++;
                    break;
                }
            }
        }

        boolean matches =  (found == 4)
                && (items.size() == 0);

        matches = matches && center.test(ctx.getItem(4));

        matches = matches && fuel.test(ctx.getFuel());

        return matches;
    }

    /**
     *
     * @param p_77572_1_
     * @return
     */
    @Override
    public ItemStack assemble(AltarContext p_77572_1_) {

        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return true;
    }


    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
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
