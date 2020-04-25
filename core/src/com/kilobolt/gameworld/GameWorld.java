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

    public int midPointY;

    private GameState currentState;

    public enum GameState {
        READY, RUNNING, GAMEOVER, HIGHSCORE, ISMENU
    }

    public GameWorld(int midPointY){
        currentState = GameState.READY;
        this.midPointY = midPointY;
        bird = new Bird(33, midPointY - 5, 17, 12);
        scroller = new ScrollHandler(this, midPointY + 66);

        ground = new Rectangle(0, midPointY + 66, 136, 11);
    }

    public void update(float delta){
        switch(currentState){
            case READY:
                updateReady(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            default:
                break;
        }
    }

    private void updateReady(float delta){

    }

    public void updateRunning(float delta){
        if(delta > .15f){
            delta = .15f;
        }

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
            currentState = GameState.GAMEOVER;

            if(score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(score);
                currentState = GameState.HIGHSCORE;
            }
        }
    }

    public void addScore(int increment){
        score += increment;
    }

    public boolean isReady(){
        return currentState == GameState.READY;
    }

    public boolean isGameOver(){
        return currentState == GameState.GAMEOVER;
    }

    public boolean isHighScore(){
        return currentState == GameState.HIGHSCORE;
    }

    public boolean isMenu() { return currentState == GameState.ISMENU; };

    public void start(){
        currentState = GameState.RUNNING;
    }

    public void restart(){
        currentState = GameState.READY;
        score = 0;
        bird.onRestart(midPointY - 5);
        scroller.onRestart();
        currentState = GameState.READY;
    }

    public int getMidPointY(){
        return midPointY;
    };

    public void ready(){
        currentState = GameState.READY;
    }

    public int getScore(){ return score; }
    public Bird getBird(){
        return bird;
    }
    public ScrollHandler getScroller(){ return scroller; }
}
