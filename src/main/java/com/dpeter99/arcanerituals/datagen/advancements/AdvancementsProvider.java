package com.dpeter99.arcanerituals.datagen.advancements;

import com.dpeter99.arcanerituals.ArcaneRituals;
import com.dpeter99.arcanerituals.advancements.BloodDrainTrigger;
import com.dpeter99.arcanerituals.registry.ARRegistry;
import com.dpeter99.bloodylib.datagen.util.AdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.entity.EntityType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.function.Consumer;

public class AdvancementsProvider extends AdvancementProvider {

    public static final ResourceLocation STORY_ROOT_TITLE  = ArcaneRituals.location("advancements.story.root.title");
    public static final ResourceLocation STORY_ROOT_DESCRIPTION  = ArcaneRituals.location("advancements.story.root.description");

    public static final ResourceLocation STORY_FRESH_BLOOD  = ArcaneRituals.location("advancements.story.fresh_blood");

    public AdvancementsProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerAdvancement(Consumer<Advancement> consumer) {
        Advancement root = Advancement.Builder.advancement()
                .display(ARRegistry.VIAL.get(),
                        new TranslatableComponent(STORY_ROOT_TITLE.toString()),
                        new TranslatableComponent(STORY_ROOT_DESCRIPTION.toString()),
                        new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"),
                        FrameType.TASK,
                        true, true, true)
                .addCriterion("test1", new BloodDrainTrigger.Instance(EntityType.SHEEP.getRegistryName(), EntityPredicate.Composite.ANY))
                .build(ArcaneRituals.location("main/root"));
        consumer.accept(root);

        Advancement fresh_blood = Advancement.Builder.advancement()
                .display(ARRegistry.VIAL.get(),
                        new TranslatableComponent(getTitle(STORY_FRESH_BLOOD)),
                        new TranslatableComponent(getDesc(STORY_FRESH_BLOOD)),
                        new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"),
                        FrameType.TASK,
                        true, true, true)
                .addCriterion("test1", new BloodDrainTrigger.Instance(EntityType.SHEEP.getRegistryName(), EntityPredicate.Composite.ANY))
                .parent(root)
                .build(ArcaneRituals.location("story/fresh_blood"));
        consumer.accept(fresh_blood);

    }

    public static String getTitle(ResourceLocation location){
        return location.toString()+".title";
    }

    public static String getDesc(ResourceLocation location){
        return location.toString()+".description";
    }

    @Override
    public String getName() {
        return "Advancements";
    }
}