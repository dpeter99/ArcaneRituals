package com.dpeter99.arcanerituals.datagen.recipes;

import com.dpeter99.arcanerituals.registry.ARRegistry;
import com.dpeter99.bloodylib.datagen.util.BloodyRecipeProvider;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Blocks;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
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