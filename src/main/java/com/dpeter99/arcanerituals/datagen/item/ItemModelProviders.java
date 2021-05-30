package com.dpeter99.arcanerituals.datagen.item;

import com.dpeter99.arcanerituals.ArcaneRituals;
import com.dpeter99.arcanerituals.registry.ARRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelBuilder.Perspective;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ItemModelProviders extends ItemModelProvider {

    public ItemModelProviders(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, ArcaneRituals.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Altar Item
        demonicAltar();

        //Sacraficial knife

        //withExistingParent(name(ARRegistry.SACRIFICIAL_KNIFE), modLoc("block/demonic_altar_v2"));

        generatedItem(ARRegistry.SACRIFICIAL_KNIFE, "dagger_of_sacrifice");

        generatedItem(ARRegistry.BAT_WING);
        generatedItem(ARRegistry.BLOOD_HAMMER);
        generatedItem(ARRegistry.HAMMER);
        generatedItem(ARRegistry.IRON_RING);

    }

    private void demonicAltar() {
        String name = ARRegistry.getName(ARRegistry.DEMONIC_ALTAR_ITEM).getPath();

        withExistingParent(name, modLoc("block/demonic_altar_v2")).transforms().transform(Perspective.GUI)
                .rotation(22.5f, 45, 0).scale(0.6f).end().transform(Perspective.FIRSTPERSON_LEFT).scale(0.5f)
                .rotation(0, 45, 0).end().transform(Perspective.FIRSTPERSON_RIGHT).scale(0.5f).rotation(0, 45, 0).end()
                .transform(Perspective.GROUND).scale(0.3f).translation(0, 3, 0).end()
                .transform(Perspective.THIRDPERSON_LEFT).scale(0.4f).rotation(67.5f, 45, 0).translation(0, 3, 1).end()
                .transform(Perspective.THIRDPERSON_RIGHT).scale(0.4f).rotation(67.5f, 45, 0).translation(0, 3, 1).end()
                .end();
    }

    public void generatedItem(RegistryObject<?> a){
        String name = name(a);
        singleTexture("item/" + name,       // destination
                mcLoc("item/generated"),                // "parent": ###
                "layer0",                            // ###: TEXTURE
                modLoc("item/" + name));   // LAYER: ###
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