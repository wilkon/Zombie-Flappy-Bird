package com.kilobot.zombiebird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.kilobolt.gameworld.GameRenderer;
import com.kilobolt.gameworld.GameWorld;

public class GameScreen implements Screen {

    private GameWorld world;
    private GameRenderer renderer;

    public GameScreen(){
        world = new GameWorld();
        renderer = new GameRenderer(world);
    }

    @Override
    public void render(float delta) {
        world.update(delta);
        renderer.render();
    }

    @Override
    public void show() {

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
