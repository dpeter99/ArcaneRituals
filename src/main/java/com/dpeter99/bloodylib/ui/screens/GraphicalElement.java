package com.dpeter99.bloodylib.ui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IRenderable;

public abstract class GraphicalElement extends AbstractGui implements IRenderable {

    protected BloodyContainerScreen<?> screen;

    public void init(BloodyContainerScreen<?> s){
        this.screen = s;
    }

    public enum RenderLayer{
        bg
    }


    public abstract RenderLayer getRenderLayer();

    public abstract void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_);

}
