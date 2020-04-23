package com.kilobolt.gameworld;

import com.badlogic.gdx.Gdx;

public class GameRenderer {
    GameWorld myWorld;
    public GameRenderer(GameWorld world){
        myWorld = world;
    }

    public void render(){
        Gdx.app.log("GameRenderer", "rendering");
    }
}
