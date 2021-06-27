package com.dpeter99.arcanerituals.datagen.block.state;

import com.dpeter99.arcanerituals.ArcaneRituals;
import com.dpeter99.arcanerituals.registry.ARRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStateProvider extends net.minecraftforge.client.model.generators.BlockStateProvider {
    public BlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ArcaneRituals.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {


        simpleBlock(ARRegistry.DEMONIC_ALTAR.get(), models().getExistingFile(ArcaneRituals.location("block/demonic_altar")) );
    }
}