package com.kilobolt.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameRenderer {
    GameWorld myWorld;

    private OrthographicCamera camera;

    public GameRenderer(GameWorld world){
        myWorld = world;

        camera = new OrthographicCamera();
        camera.setToOrtho(true, 136, 204);

    }

    public void render(){
        Gdx.app.log("GameRenderer", "rendering");

        //setting our background black - prevents flickering
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }
}
