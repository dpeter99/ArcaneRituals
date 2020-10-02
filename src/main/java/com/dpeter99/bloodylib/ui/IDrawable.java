package com.dpeter99.ArcaneRituals.util.ui;

import com.mojang.blaze3d.matrix.MatrixStack;

public interface IDrawable {

    public void draw(MatrixStack matrixStack, int guiLeft, int guiTop);

}
