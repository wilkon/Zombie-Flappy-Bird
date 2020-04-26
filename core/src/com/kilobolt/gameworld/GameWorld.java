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
    private float runTime = 0;

    public int midPointY;

    private GameState currentState;

    private GameRenderer renderer;

    public enum GameState {
        READY, RUNNING, GAMEOVER, HIGHSCORE, MENU
    }

    public GameWorld(int midPointY){
        currentState = GameState.MENU;
        this.midPointY = midPointY;
        bird = new Bird(33, midPointY - 5, 17, 12);
        scroller = new ScrollHandler(this, midPointY + 66);
        ground = new Rectangle(0, midPointY + 66, 137, 11);
    }

    public void update(float delta){
        runTime += delta;

        switch(currentState){
            case READY:
            case MENU:
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
        bird.updateReady(runTime);
        scroller.updateReady(delta);
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
            AssetLoader.deathSound.play();
            currentState = GameState.GAMEOVER;

            if(score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(score);
                currentState = GameState.HIGHSCORE;
            }
        }
    }

    public void setRenderer(GameRenderer renderer){
        this.renderer = renderer;
    }

    public void addScore(int increment){
        score += increment;
    }

    public boolean isReady(){
        return currentState == GameState.READY;
    }

    public boolean isRunning(){
        return currentState == GameState.RUNNING;
    }

    public boolean isGameOver(){
        return currentState == GameState.GAMEOVER;
    }

    public boolean isHighScore(){
        return currentState == GameState.HIGHSCORE;
    }

    public boolean isMenu() { return currentState == GameState.MENU; };

    public void start(){
        currentState = GameState.RUNNING;
    }

    public void restart(){
        score = 0;
        bird.onRestart(midPointY - 5);
        scroller.onRestart();
        ready();
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
