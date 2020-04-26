package com.kilobolt.zombiebird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.kilobolt.gameworld.GameRenderer;
import com.kilobolt.gameworld.GameWorld;
import com.kilobolt.zbhelpers.InputHandler;

public class GameScreen implements Screen {

    private GameWorld world;
    private GameRenderer renderer;

    private float runTime = 0;

    public GameScreen(){

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 136;
        float gameHeight = screenHeight / (screenWidth / gameWidth);

        int midPointY = (int) (gameHeight / 2);

        world = new GameWorld(midPointY);

        Gdx.input.setInputProcessor(new InputHandler(world,
                screenWidth/gameWidth,
                screenHeight/gameHeight
        ));

        renderer = new GameRenderer(world, (int) gameHeight, midPointY);
    }

    @Override
    public void render(float delta) {
        // keeping track of total runtime
        runTime += delta;

        world.update(delta);
        renderer.render(delta, runTime);
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
