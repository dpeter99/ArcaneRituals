package com.dpeter99.arcanerituals.datagen.tags;

import com.dpeter99.arcanerituals.ArcaneRituals;
import com.dpeter99.arcanerituals.registry.ARRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;


public class ItemTagProviders extends ItemTagsProvider {

    public static final Tag.Named<Item> CURIO_RING = ItemTags.bind(new ResourceLocation("curios", "ring").toString());

    public ItemTagProviders(DataGenerator generatorIn, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, blockTagProvider, ArcaneRituals.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        //ITag<?> a = TagRegistryManager.get(new ResourceLocation("item")).getAllTags().getTag(new ResourceLocation("curio", "ring"));

        this.tag(CURIO_RING).add(ARRegistry.IRON_RING.get());
        this.tag(CURIO_RING).add(ARRegistry.RING_OF_PROTECTION_1.get(), ARRegistry.RING_OF_PROTECTION_2.get(),ARRegistry.RING_OF_PROTECTION_3.get());
    }

}