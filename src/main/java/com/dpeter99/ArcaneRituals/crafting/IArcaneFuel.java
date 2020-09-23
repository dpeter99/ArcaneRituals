package com.dpeter99.ArcaneRituals.crafting;

import com.google.gson.JsonObject;
import net.minecraft.client.gui.ResourceLoadProgressGui;
import net.minecraft.util.ResourceLocation;

public interface IArcaneFuel {

    ResourceLocation getResourceName();

    IArcaneFuel parse(JsonObject jsonObject);

    boolean match(IArcaneFuel other);

}
