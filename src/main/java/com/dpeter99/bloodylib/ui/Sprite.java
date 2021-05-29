package com.dpeter99.bloodylib.ui;

import com.dpeter99.bloodylib.math.Vector2i;

public class Sprite {

    Vector2i start;
    Vector2i size;

    public Sprite(Vector2i start, Vector2i size) {
        this.start = start;
        this.size = size;
    }

    public Vector2i getStart() {
        return start;
    }

    public Vector2i getSize() {
        return size;
    }

    public int getSizeX() {
        return size.x;
    }

    public int getSizeY() {
        return size.y;
    }

}
