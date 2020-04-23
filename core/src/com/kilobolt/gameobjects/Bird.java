package com.kilobolt.gameobjects;

import com.badlogic.gdx.math.Vector2;

public class Bird {
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private float rotations;
    private int width;
    private int height;

    public Bird(float x, float y, int width, int height){
        this.width = width;
        this.height = height;
        this.position.set(x, y);
        this.velocity = new Vector2(0, 0);
        this.acceleration = new Vector2(0, 460);
    }

    public void update(float delta){
        // increasing velocity to accommodate for gravity (acceleration.y) against delta time
        velocity.add(acceleration.cpy().scl(delta));

        //max drop velocity (terminal velocity)
        if(velocity.y > 200){
            velocity.y = 200;
        }

        //changing our position based on the increase in velocity against delta time
        position.add(velocity.cpy().scl(delta));
    }

    public void onClick(){
        //velocity is reduced since we're flapping to elevate
        velocity.y = -140;
    }

    public float getX(){
        return position.x;
    }

    public float getY(){
        return position.y;
    }

    public float getRotations() {
        return rotations;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
