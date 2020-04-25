package com.kilobolt.screens;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kilobolt.tweenaccessors.SpriteAccessor;
import com.kilobolt.zbhelpers.AssetLoader;
import com.kilobolt.zombiebird.GameScreen;
import com.kilobolt.zombiebird.ZBGame;

public class SplashScreen  implements Screen {

    private TweenManager manager;
    private SpriteBatch batcher;
    private Sprite sprite;
    private ZBGame game;

    public SplashScreen(ZBGame game){
        this.game = game;
    }

    @Override
    public void show() {
        sprite = new Sprite(AssetLoader.logo);
        sprite.setColor(1, 1, 1,0);

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        // desired width is scaling our width to 70%,
        // so when we calculate values, it's based on this 70%.
        float desiredWidth = width * .7f;
        float scale = desiredWidth / sprite.getWidth();

        sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);

        //centering position
        sprite.setPosition((width / 2) - (sprite.getWidth() / 2),
                (height / 2) - (sprite.getHeight() / 2));

        setupTween();
        batcher = new SpriteBatch();
    }

    private void setupTween(){
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        manager = new TweenManager();

        //creating callback for when tween is completed
        TweenCallback cb = new TweenCallback(){
            @Override
            public void onEvent(int type, BaseTween<?> source){
                game.setScreen(new GameScreen());
            }
        };

        // sprite will use a yoyo like alpha change
        // sending a complete when finished to invoke our callback
        Tween.to(sprite, SpriteAccessor.ALPHA, .8f).target(1)
                .ease(TweenEquations.easeInOutQuad).repeatYoyo(1, .4f)
                .setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE)
                .start(manager);
    }

    @Override
    public void render(float delta) {
        manager.update(delta);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batcher.begin();
        sprite.draw(batcher);
        batcher.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
