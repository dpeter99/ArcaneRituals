package com.dpeter99.arcanerituals.datagen.tags;

import com.dpeter99.arcanerituals.ArcaneRituals;
import com.dpeter99.arcanerituals.registry.ARRegistry;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagRegistryManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;

import javax.annotation.Nullable;


public class ItemTagProviders extends ItemTagsProvider {

    public static final ITag.INamedTag<Item> CURIO_RING = ItemTags.bind(new ResourceLocation("curios", "ring").toString());

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