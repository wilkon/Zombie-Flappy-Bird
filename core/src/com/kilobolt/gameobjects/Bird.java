package com.kilobolt.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Bird {
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private float rotation;
    private int width;
    private int height;

    private Circle boundingCircle;

    public Bird(float x, float y, int width, int height){
        this.width = width;
        this.height = height;
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
        this.acceleration = new Vector2(0, 460);
        boundingCircle = new Circle();
    }

    public void update(float delta){
        // increasing velocity to accommodate for gravity (acceleration.y) against delta time
        velocity.add(acceleration.cpy().scl(delta));

        // we want our bounding circle to by slightly towards the front of bird
        boundingCircle.set(position.x + 9, position.y + 6, 6.5f);

        //max drop velocity (terminal velocity)
        if(velocity.y > 200){
            velocity.y = 200;
        }

        //rotating counter-clockwise (flapping up)
        if(velocity.y < 0){
            rotation -= 600 * delta;

            //maximum upwards angle
            if(rotation < -20){
                rotation = -20;
            }
        }

        // rotating clockwise (falling)
        if(isFalling()){
            rotation += 480 * delta; //keep in mind our math is inclusive of Y Down
            // also note our use of delta for the rotations; moving in accordance to frames/sec

            // max angle to fall (essentially vertical or perpendicular)
            if(rotation > 90){
                rotation = 90;
            }
        }

        //changing our position based on the increase in velocity against delta time
        position.add(velocity.cpy().scl(delta));
    }

    public void onClick(){
        //velocity is reduced since we're flapping to elevate
        velocity.y = -140;
    }

    public boolean isFalling(){
        return velocity.y > 110;
    }

    public boolean shouldntFlap(){
        return velocity.y > 70;
    }

    public Circle getBoundingCircle(){
        return boundingCircle;
    }

    public float getX(){
        return position.x;
    }

    public float getY(){
        return position.y;
    }

    public float getRotations() {
        return rotation;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
