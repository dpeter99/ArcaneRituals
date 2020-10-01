package com.dpeter99.ArcaneRituals.util.ui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.AbstractGui;

public class TextureRegion {
    int startX;
    int startY;

    int sizeX;
    int sizeY;

    int sourceHeight;
    int sourceWidth;

    public TextureRegion(int startX, int startY, int sizeX, int sizeY) {
        this.startX = startX;
        this.startY = startY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public TextureRegion(int startX, int startY, int sizeX, int sizeY, int sourceWidth, int sourceHeight) {
        this.startX = startX;
        this.startY = startY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        this.sourceWidth = sourceWidth;
        this.sourceHeight = sourceHeight;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public void blit(MatrixStack matrixStack, int destX, int destY, int blitoffset){
        AbstractGui.blit(matrixStack, destX, destY, blitoffset, startX, startY, sizeX, sizeY, sourceHeight, sourceWidth);
    }

    public void blitSized(MatrixStack matrixStack, int destX, int destY, int sizeX, int sizeY){

        AbstractGui.blit(matrixStack, destX, destY, sizeX, sizeY, startX, startY, this.sizeX, this.sizeY, sourceWidth, sourceHeight);

    }
}
