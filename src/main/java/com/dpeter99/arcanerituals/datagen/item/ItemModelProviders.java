package com.dpeter99.arcanerituals.datagen.item;

import com.dpeter99.arcanerituals.ArcaneRituals;
import com.dpeter99.arcanerituals.registry.ARRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelProviders extends ItemModelProvider {

    public ItemModelProviders(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, ArcaneRituals.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Exploder
        String vanillaExplosive = ARRegistry.getName(ARRegistry.DEMONIC_ALTAR_ITEM).getPath();

        withExistingParent(vanillaExplosive, modLoc("block/demonic_altar_v2"));

    }
}