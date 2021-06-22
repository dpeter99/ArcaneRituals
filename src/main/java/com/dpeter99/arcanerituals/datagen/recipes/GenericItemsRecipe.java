package com.dpeter99.arcanerituals.datagen.recipes;

import com.dpeter99.arcanerituals.advancements.BloodDrainTrigger;
import com.dpeter99.arcanerituals.fluids.Blood;
import com.dpeter99.arcanerituals.items.ItemVial;
import com.dpeter99.arcanerituals.registry.ARRegistry;
import com.dpeter99.bloodylib.datagen.util.BloodyRecipeProvider;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.*;
import net.minecraft.block.Blocks;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;

import java.util.function.Consumer;

public class GenericItemsRecipe extends BloodyRecipeProvider {

    public GenericItemsRecipe(Consumer<IFinishedRecipe> consumer) {
        super(consumer);
    }

    @Override
    public void init() {
        ShapedRecipeBuilder.shaped(ARRegistry.IRON_RING.get(), 2)
                .pattern(" X ")
                .pattern("X X")
                .pattern(" X ")
                .define('X',Items.IRON_INGOT)
                .unlockedBy("has_iron", has(Items.IRON_INGOT))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ARRegistry.HAMMER.get(), 1)
                .pattern(" X ")
                .pattern(" WX")
                .pattern("W  ")
                .define('X',Items.IRON_INGOT)
                .define('W',Items.STICK)
                .unlockedBy("has_iron", has(Items.IRON_INGOT))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ARRegistry.VIAL.get(), 2)
                .pattern("C ")
                .pattern("G ")
                .define('C',Items.CLAY_BALL)
                .define('G',Items.GLASS)
                .unlockedBy("has_glass", has(Items.GLASS))
                .save(consumer);

        ShapedRecipeBuilder.shaped(ARRegistry.DEMONIC_ALTAR_ITEM.get(),1)
                .pattern("I I")
                .pattern("SVS")
                .pattern("SSS")
                .define('I', Items.IRON_BLOCK)
                .define('S', Items.COBBLESTONE)
                //Ingredient.of(ItemVial.make(Blood.makeFluidStack(250, EntityType.SHEEP.getRegistryName())))
                /*TODO: Vial that has any blood*/
                .define('V', Items.NETHERITE_INGOT)
                .unlockedBy("first_blood", new BloodDrainTrigger.Instance(EntityType.SHEEP.getRegistryName(), EntityPredicate.AndPredicate.ANY))
                .save(consumer);
    }


    protected static InventoryChangeTrigger.Instance has(IItemProvider p_200403_0_) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(p_200403_0_).build());
    }

    protected static InventoryChangeTrigger.Instance has(ITag<Item> p_200409_0_) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(p_200409_0_).build());
    }

    protected static InventoryChangeTrigger.Instance inventoryTrigger(ItemPredicate... p_200405_0_) {
        return new InventoryChangeTrigger.Instance(EntityPredicate.AndPredicate.ANY, MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY, p_200405_0_);
    }
}