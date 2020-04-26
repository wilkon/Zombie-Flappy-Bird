package com.kilobolt.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.kilobolt.zbhelpers.AssetLoader;

public class Bird {
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private float rotation;
    private int width;
    private int height;
    private float originalY;

    private Circle boundingCircle;

    private boolean isAlive;

    public Bird(float x, float y, int width, int height){
        this.width = width;
        this.height = height;
        this.originalY = y;
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
        this.acceleration = new Vector2(0, 460);
        boundingCircle = new Circle();
        isAlive = true;
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

        // ceiling check
        if(position.y < -13){
            position.y = -13;
            velocity.y = 0;
        }
        //changing our position based on the increase in velocity against delta time
        position.add(velocity.cpy().scl(delta));

        // Set the circle's center to be (9, 6) with respect to the bird.
        // Set the circle's radius to be 6.5f;
        boundingCircle.set(position.x + 9, position.y + 6, 6.5f);

        //rotating counter-clockwise (flapping up)
        if(velocity.y < 0){
            rotation -= 600 * delta;

            //maximum upwards angle
            if(rotation < -20){
                rotation = -20;
            }
        }

        // rotating clockwise (falling)
        if(isFalling() || !isAlive){
            rotation += 480 * delta; //keep in mind our math is inclusive of Y Down
            // also note our use of delta for the rotations; moving in accordance to frames/sec

            // max angle to fall (essentially vertical or perpendicular)
            if(rotation > 90){
                rotation = 90;
            }
        }

    }

    public void updateReady(float runTime){
        position.y = originalY;
    }

    public void onClick(){
        //velocity is reduced since we're flapping to elevate
        if(isAlive){
            AssetLoader.flapSound.play();
            velocity.y = -140;
        }
    }

    public void die(){
        isAlive = false;
        velocity.y = 0;
    }

    public void onRestart(int y){
        rotation = 0;
        position.y = y;
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = 0;
        acceleration.y = 460;
        isAlive = true;
    }

    public void decelerate(){
        acceleration.y = 0;
    }

    public boolean isAlive(){
        return isAlive;
    }

    public boolean isFalling(){
        return velocity.y > 110;
    }

    public boolean shouldntFlap(){
        return velocity.y > 70 || !isAlive;
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
