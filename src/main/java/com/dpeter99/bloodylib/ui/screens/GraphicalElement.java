package com.dpeter99.bloodylib.ui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;

public abstract class GraphicalElement extends GuiComponent implements Widget {

    protected BloodyContainerScreen<?> screen;

    public void init(BloodyContainerScreen<?> s){
        this.screen = s;
    }

    public enum RenderLayer{
        bg
    }


    public abstract RenderLayer getRenderLayer();

    public abstract void render(PoseStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_);

}
