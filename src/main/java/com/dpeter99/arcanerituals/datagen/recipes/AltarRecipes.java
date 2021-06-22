package com.dpeter99.arcanerituals.datagen.recipes;

import com.dpeter99.arcanerituals.ArcaneRituals;
import com.dpeter99.arcanerituals.crafting.ArcaneFuel;
import com.dpeter99.arcanerituals.datagen.recipes.builders.AltarRecipeBuilder;
import com.dpeter99.arcanerituals.fluids.Blood;
import com.dpeter99.arcanerituals.registry.ARRegistry;
import com.dpeter99.arcanerituals.tileentities.AltarTileEntity;
import com.dpeter99.bloodylib.datagen.util.BloodyRecipeProvider;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.Items;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.function.Consumer;

public class AltarRecipes extends BloodyRecipeProvider {


    public AltarRecipes(Consumer<IFinishedRecipe> consumer) {
        super(consumer);
    }

    @Override
    public void init() {
        AltarRecipeBuilder.blood(ARRegistry.RING_OF_PROTECTION_1.get(),1, 100)
                .fuel(Blood.makeFluidStack(100,EntityType.SHEEP.getRegistryName()))
                .addIngredient(Items.IRON_CHESTPLATE)
                .addIngredient(Items.IRON_INGOT)
                .addIngredient(ARRegistry.GOLDEN_RUNE_PLATE.get())
                .addIngredient(ARRegistry.GOLDEN_RUNE_PLATE.get())
                .setCenterIngredient(ARRegistry.IRON_RING.get())
                .save(consumer);

        AltarRecipeBuilder.blood(ARRegistry.RING_OF_PROTECTION_2.get(),1, 100)
                .fuel(Blood.makeFluidStack(100,EntityType.SHEEP.getRegistryName()))
                .addIngredient(Items.IRON_CHESTPLATE)
                .addIngredient(Items.DIAMOND)
                .addIngredient(ARRegistry.GOLDEN_RUNE_PLATE.get())
                .addIngredient(ARRegistry.GOLDEN_RUNE_PLATE.get())
                .setCenterIngredient(ARRegistry.RING_OF_PROTECTION_1.get())
                .save(consumer);

        AltarRecipeBuilder.blood(ARRegistry.RING_OF_PROTECTION_3.get(),1, 100)
                .fuel(Blood.makeFluidStack(100,EntityType.SHEEP.getRegistryName()))
                .addIngredient(Items.IRON_CHESTPLATE)
                .addIngredient(Items.NETHERITE_INGOT)
                .addIngredient(ARRegistry.GOLDEN_RUNE_PLATE.get())
                .addIngredient(ARRegistry.GOLDEN_RUNE_PLATE.get())
                .setCenterIngredient(ARRegistry.RING_OF_PROTECTION_2.get())
                .save(consumer);
    }



}
