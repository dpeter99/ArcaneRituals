package com.dpeter99.bloodylib.math;

public class Vector2i {

    public int x;
    public int y;


    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2i of(int x, int y){
        return new Vector2i(x,y);
    }


    public static Vector2i subtract(Vector2i left, Vector2i right) {
        return new Vector2i(left.x- right.x,left.y- right.y);
    }

    public Vector2i subtract(Vector2i right)
    {
        return subtract(this, right);
    }


    public static Vector2i subtract(Vector2i left, int right)
    {
        return new Vector2i(left.x- right,left.y- right);
    }

    public Vector2i subtract(int right)
    {
        return subtract(this, right);
    }




    public static Vector2i add(Vector2i left, Vector2i right)
    {
        return new Vector2i(left.x+ right.x,left.y+ right.y);
    }

    public Vector2i add(Vector2i right)
    {
        return add(this, right);
    }


    public static Vector2i add(Vector2i left, int right)
    {
        return new Vector2i(left.x+ right,left.y+ right);
    }

    public Vector2i add(int right)
    {
        return add(this, right);
    }


    public static Vector2i addX(Vector2i left, int right)
    {
        return new Vector2i(left.x+ right,left.y);
    }

    public Vector2i addX(int right)
    {
        return addX(this, right);
    }

    public static Vector2i addY(Vector2i left, int right)
    {
        return new Vector2i(left.x,left.y+right);
    }

    public Vector2i addY(int right)
    {
        return addY(this, right);
    }

}
