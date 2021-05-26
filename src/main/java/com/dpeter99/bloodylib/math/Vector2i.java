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



    public static Vector2i multiply(Vector2i left, Vector2i right) {
        return new Vector2i(left.x*right.x,left.y*right.y);
    }

    public Vector2i multiply(Vector2i right) {return Vector2i.multiply(this,right); }


    public Vector2i multiply(int right) {return new Vector2i(this.x*right,this.y*right); }

    public Vector2i multiply(float right) {
        int new_x = (int)Math.floor(this.x*right);
        int new_y = (int)Math.floor(this.y*right);

        return new Vector2i(new_x,new_y);
    }

    public Vector2i divide(float right) {
        int new_x = (int)Math.floor(this.x/right);
        int new_y = (int)Math.floor(this.y/right);

        return new Vector2i(new_x,new_y);
    }


    public static float distance(Vector2i value1, Vector2i value2)
    {
        Vector2i difference = subtract(value1, value2);
        float ls = Vector2i.dot(difference, difference);
        return (float) Math.sqrt(ls);
    }

    public static float dot(Vector2i value1, Vector2i value2)
    {
        return value1.x * value2.x +
                value1.y * value2.y;
    }


    public static float angle(Vector2i a, Vector2i b){
        return (float) Math.atan2((double) b.y - a.y,(double) b.x - a.x);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "{x:"+x+" y: "+y+"}";
    }

}
