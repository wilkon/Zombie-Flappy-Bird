package com.kilobolt.gameworld;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.kilobolt.gameobjects.Bird;
import com.kilobolt.gameobjects.ScrollHandler;
import com.kilobolt.zbhelpers.AssetLoader;

public class GameWorld {
    private Bird bird;
    private ScrollHandler scroller;

    private Rectangle ground;

    private int score = 0;

    public GameWorld(int midPointY){
        bird = new Bird(33, midPointY - 5, 17, 12);
        scroller = new ScrollHandler(this, midPointY + 66);

        ground = new Rectangle(0, midPointY + 66, 136, 11);
    }

    public void update(float delta){
        bird.update(delta);
        scroller.update(delta);

        if(scroller.collides(bird) && bird.isAlive()){
            scroller.stop();
            bird.die();
            AssetLoader.deathSound.play();
        }

        if(Intersector.overlaps(bird.getBoundingCircle(), ground)){
            scroller.stop();
            bird.die();
            bird.decelerate();
        }
    }

    public void addScore(int increment){
        score += increment;
    }

    public int getScore(){ return score; }
    public Bird getBird(){
        return bird;
    }
    public ScrollHandler getScroller(){ return scroller; }
}
