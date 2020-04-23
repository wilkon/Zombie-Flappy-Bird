package com.kilobot.zombiebird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class GameScreen implements Screen {
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // sets color with RGB 10, 15, 230 and opacity of 1 (100%)
        Gdx.gl.glClearColor(10/255.0f, 15/255.0f, 230/255.0f, 1f);

        // fills the screen with selected (^) color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("GameScreen", "resizing");
    }

    @Override
    public void pause() {
        Gdx.app.log("GameScreen", "pausing");
    }

    @Override
    public void resume() {
        Gdx.app.log("GameScreen", "resuming");
    }

    @Override
    public void hide() {
        Gdx.app.log("GameScreen", "hiding");
    }

    @Override
    public void dispose() {

    }
}
