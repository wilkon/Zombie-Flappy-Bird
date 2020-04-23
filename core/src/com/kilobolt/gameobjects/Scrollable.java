package com.kilobolt.gameobjects;

import com.badlogic.gdx.math.Vector2;

public class Scrollable {
    protected Vector2 position;
    protected Vector2 velocity;
    protected int width;
    protected int height;
    protected boolean isScrolledLeft;

    public Scrollable(float x, float y, int width, int height, float scrollSpeed){
        position = new Vector2(x, y);
        velocity = new Vector2(scrollSpeed, 0);
        this.width = width;
        this.height = height;
        isScrolledLeft = false;
    }

    public float getX(){
        return position.x;
    }
    public float getY(){
        return position.y;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
}
