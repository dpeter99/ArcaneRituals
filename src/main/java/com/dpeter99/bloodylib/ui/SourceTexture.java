package com.dpeter99.bloodylib.ui;

import net.minecraft.util.ResourceLocation;

public class SourceTexture {

    public ResourceLocation location;

    public int width;
    public int height;

    public SourceTexture(ResourceLocation location, int width, int height) {
        this.location = location;
        this.width = width;
        this.height = height;
    }
}
