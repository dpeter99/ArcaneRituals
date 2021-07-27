package com.dpeter99.arcanerituals.datagen.lang;

import java.util.Objects;

import com.dpeter99.arcanerituals.ArcaneRituals;
import com.dpeter99.arcanerituals.fluids.Blood;
import com.dpeter99.arcanerituals.registry.ARRegistry;
import com.dpeter99.bloodylib.datagen.util.AdvancementProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.LanguageProvider;

import com.dpeter99.arcanerituals.datagen.advancements.AdvancementsProvider;

public class EnUsLangProvider extends LanguageProvider {

    public EnUsLangProvider(DataGenerator generatorIn) {
        super(generatorIn, ArcaneRituals.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(ARRegistry.DEMONIC_ALTAR.get(), "Demonic Altar");

        add(ARRegistry.GOLDEN_PLATE.get(),"Golden Plate");
        add(ARRegistry.GOLDEN_RUNE_PLATE.get(),"Golden Rune Plate");
        add(ARRegistry.SACRIFICIAL_KNIFE.get(), "Sacrificial Knife");
        add(ARRegistry.VIAL.get(), "Vial");

        add(ARRegistry.BLOOD.get(), "Blood");

        add(ArcaneRituals.TAB,"Arcane Rituals");

        add(AdvancementsProvider.STORY_ROOT_TITLE, "Arcane Rituals");
        add(AdvancementsProvider.STORY_ROOT_DESCRIPTION, "This is where all it starts");

        add(AdvancementsProvider.getTitle(AdvancementsProvider.STORY_FRESH_BLOOD),"Fresh Blood");
        add(AdvancementsProvider.getDesc(AdvancementsProvider.STORY_FRESH_BLOOD),"Fresh Blood");
    }

    private void add(Blood fluid, String name) {
        add(fluid.getRegistryName().toString(), name);
    }

    private void add(ResourceLocation res, String name) {
        add(res.toString(), name);
    }

    private void add(CreativeModeTab group, String name) {
        add("itemGroup." + Objects.requireNonNull(group.getRecipeFolderName()), name);
    }
}