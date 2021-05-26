package com.dpeter99.arcanerituals.datagen.item;

import com.dpeter99.arcanerituals.ArcaneRituals;
import com.dpeter99.arcanerituals.registry.ARRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ItemModelProviders extends ItemModelProvider {

    public ItemModelProviders(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, ArcaneRituals.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Altar Item
        String vanillaExplosive = ARRegistry.getName(ARRegistry.DEMONIC_ALTAR_ITEM).getPath();

        withExistingParent(vanillaExplosive, modLoc("block/demonic_altar_v2"));

        //Sacraficial knife

        //withExistingParent(name(ARRegistry.SACRIFICIAL_KNIFE), modLoc("block/demonic_altar_v2"));

        generatedItem(ARRegistry.SACRIFICIAL_KNIFE, "dagger_of_sacrifice");

    }

    public void generatedItem(RegistryObject<?> a){
        String name = name(a);
        singleTexture("item/" + name,       // destination
                mcLoc("item/generated"),                // "parent": ###
                "layer0",                            // ###: TEXTURE
                modLoc("items/" + name));   // LAYER: ###
    }

    public void generatedItem(RegistryObject<?> a, String texture){
        String name = name(a);
        singleTexture("item/" + name,       // destination
                mcLoc("item/generated"),                // "parent": ###
                "layer0",                            // ###: TEXTURE
                modLoc("item/" + texture));   // LAYER: ###
    }

    public static String name(RegistryObject<?> a){
        return ARRegistry.getName(a).getPath();
    }
}