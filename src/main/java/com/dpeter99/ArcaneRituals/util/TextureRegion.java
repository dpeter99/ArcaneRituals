package com.dpeter99.ArcaneRituals.util;

public class TextureRegion {
    int startX;
    int startY;

    int sizeX;
    int sizeY;

    public TextureRegion(int startX, int startY, int sizeX, int sizeY) {
        this.startX = startX;
        this.startY = startY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
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
}
