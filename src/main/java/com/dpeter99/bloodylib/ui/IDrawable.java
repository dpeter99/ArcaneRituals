package com.dpeter99.bloodylib.ui;

import com.mojang.blaze3d.matrix.MatrixStack;

public interface IDrawable {

    public void draw(MatrixStack matrixStack, int guiLeft, int guiTop);

}
