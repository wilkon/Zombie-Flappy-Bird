package com.kilobolt.zbhelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {
    public static Texture texture, logoTexture;
    public static TextureRegion bg, grass;

    public static Animation birdAnimation;
    public static TextureRegion birdMid, birdDown, birdUp;

    public static TextureRegion skullUp, skullDown, bar;

    public static TextureRegion playButtonUp, playButtonDown;
    public static TextureRegion logo, zbLogo;
    public static TextureRegion ready, gameOver, highScore;
    public static TextureRegion scoreBoard, star, noStar, retry;

    public static Sound deathSound;
    public static Sound flapSound;
    public static Sound coinSound;
    public static Sound fallingSound;

    public static BitmapFont font, shadow, whiteFont;

    public static Preferences prefs;

    public static void load(){
        logoTexture = new Texture(Gdx.files.internal("logo.png"));
        logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        logo = new TextureRegion(logoTexture, 0, 0, 512, 114);

        texture = new Texture(Gdx.files.internal("texture.png"));

        // this forces our image to look as consistent as possible for our scaling
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        playButtonUp = new TextureRegion(texture, 0, 83, 29, 16);
        playButtonDown = new TextureRegion(texture, 29, 83, 29, 16);
        playButtonUp.flip(false, true);
        playButtonDown.flip(false, true);

        ready = new TextureRegion(texture, 59, 83, 34, 7);
        ready.flip(false, true);

        retry = new TextureRegion(texture, 59, 110, 13, 7);
        retry.flip(false, true);

        gameOver = new TextureRegion(texture, 59, 92, 46, 7);
        gameOver.flip(false, true);

        scoreBoard = new TextureRegion(texture, 111, 83, 97, 37);
        scoreBoard.flip(false, true);

        star = new TextureRegion(texture, 152, 70, 10, 10);
        noStar = new TextureRegion(texture, 165, 70, 10, 10);

        highScore = new TextureRegion(texture, 59, 101, 48, 7);
        highScore.flip(false, true);

        zbLogo = new TextureRegion(texture, 0, 55, 135, 24);
        zbLogo.flip(false, true);

        // we're going to flip on the y axis since we're setting y-down coordinate system
        bg = new TextureRegion(texture, 0, 0, 136, 43);
        bg.flip(false, true);

        grass = new TextureRegion(texture, 0, 43, 143, 11);
        grass.flip(false, true);

        birdDown = new TextureRegion(texture, 136, 0, 17,12);
        birdDown.flip(false, true);

        birdMid = new TextureRegion(texture, 153, 0, 17, 12);
        birdMid.flip(false, true);

        birdUp = new TextureRegion(texture, 170, 0, 17, 12);
        birdUp.flip(false, true);

        TextureRegion[] birds = {birdDown, birdMid, birdUp};
        birdAnimation = new Animation(0.06f, birds);
        birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        // skull up doesn't need to be flipped because it's already flipped.
        skullUp = new TextureRegion(texture,192, 0, 24, 14);

        // we're going to use a flipped skull up to create the down
        skullDown = new TextureRegion(skullUp);
        skullDown.flip(false, true);

        bar = new TextureRegion(texture, 136, 16, 22, 3);
        bar.flip(false, true);

        deathSound = Gdx.audio.newSound(Gdx.files.internal("dead.wav"));
        flapSound = Gdx.audio.newSound(Gdx.files.internal("flap.wav"));
        coinSound = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
        fallingSound = Gdx.audio.newSound(Gdx.files.internal("fall.wav"));

        font = new BitmapFont(Gdx.files.internal("text.fnt"));
        font.getData().setScale(.25f, -.25f);

        shadow = new BitmapFont(Gdx.files.internal("shadow.fnt"));
        shadow.getData().setScale(.25f, -.25f);

        whiteFont = new BitmapFont(Gdx.files.internal("whitetext.fnt"));
        whiteFont.getData().setScale(.25f, .25f);

        prefs = Gdx.app.getPreferences("ZombieBird");
        if(!prefs.contains("highScore")){
            prefs.putInteger("highSchore", 0);
        }
    }

    public static void setHighScore(int score){
        prefs.putInteger("highScore", score);
        prefs.flush(); //save
    }

    public static int getHighScore(){
        return prefs.getInteger("highScore");
    }

    public static void dispose(){
        texture.dispose();
        logoTexture.dispose();

        deathSound.dispose();
        flapSound.dispose();
        coinSound.dispose();

        shadow.dispose();
        font.dispose();
    }
}
