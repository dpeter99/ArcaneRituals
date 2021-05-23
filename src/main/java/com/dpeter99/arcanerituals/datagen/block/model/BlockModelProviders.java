package com.dpeter99.arcanerituals.datagen.block.model;

import com.dpeter99.arcanerituals.ArcaneRituals;
import com.dpeter99.arcanerituals.registry.ARRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockModelProviders extends BlockModelProvider {
    public static ModelFile demonicAltarModel;

    public BlockModelProviders(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, ArcaneRituals.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        String demonicAltar = ARRegistry.getName(ARRegistry.DEMONIC_ALTAR).getPath();

        //withExistingParent(demonicAltar,ArcaneRituals.MODID + ":block/demonic_altar_v2");

    }

}