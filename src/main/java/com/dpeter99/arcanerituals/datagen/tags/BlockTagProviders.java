package com.dpeter99.arcanerituals.datagen.tags;

import com.dpeter99.arcanerituals.ArcaneRituals;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class BlockTagProviders extends BlockTagsProvider {

    public BlockTagProviders(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, ArcaneRituals.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {

    }
}