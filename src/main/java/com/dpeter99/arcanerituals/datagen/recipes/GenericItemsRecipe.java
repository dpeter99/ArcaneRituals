package com.dpeter99.arcanerituals.datagen.recipes;

import com.dpeter99.arcanerituals.advancements.BloodDrainTrigger;
import com.dpeter99.arcanerituals.registry.ARRegistry;
import com.dpeter99.bloodylib.datagen.util.BloodyRecipeProvider;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

public class GenericItemsRecipe extends BloodyRecipeProvider {

    public GenericItemsRecipe(Consumer<FinishedRecipe> consumer) {
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
                .unlockedBy("first_blood", new BloodDrainTrigger.Instance(EntityType.SHEEP.getRegistryName(), EntityPredicate.Composite.ANY))
                .save(consumer);
    }


    protected static InventoryChangeTrigger.TriggerInstance has(ItemLike p_200403_0_) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(p_200403_0_).build());
    }

    protected static InventoryChangeTrigger.TriggerInstance has(Tag<Item> p_200409_0_) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(p_200409_0_).build());
    }

    protected static InventoryChangeTrigger.TriggerInstance inventoryTrigger(ItemPredicate... p_200405_0_) {
        return new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, p_200405_0_);
    }
}