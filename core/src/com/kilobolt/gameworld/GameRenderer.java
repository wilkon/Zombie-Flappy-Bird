package com.kilobolt.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.kilobolt.gameobjects.Bird;
import com.kilobolt.gameobjects.Grass;
import com.kilobolt.gameobjects.Pipe;
import com.kilobolt.gameobjects.ScrollHandler;
import com.kilobolt.zbhelpers.AssetLoader;

public class GameRenderer {
    GameWorld myWorld;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batcher;

    private int midPointY;
    private int gameHeight;

    private Bird myBird;

    private ScrollHandler scroller;
    private Grass frontGrass, backGrass;
    private Pipe pipe1, pipe2, pipe3;

    public static TextureRegion bg, grass;

    public static Animation birdAnimation;
    public static TextureRegion birdMid, birdDown, birdUp;

    public static TextureRegion skullUp, skullDown, bar;

    public GameRenderer(GameWorld world, int gameHeight, int midPointY){
        myWorld = world;

        this.gameHeight = gameHeight;
        this.midPointY = midPointY;

        // camera takes in the gameheight to account for any scaling
        camera = new OrthographicCamera();
        camera.setToOrtho(true, 136, gameHeight);

        // setting batcher and renderer to have the same coordinate system as our camera
        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(camera.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);

        // initializing gameobjects/assets for performance
        initGameObjects();
        initAssets();
    }

    private void initGameObjects(){
        myBird = myWorld.getBird();
        scroller = myWorld.getScroller();
        frontGrass = scroller.getFrontGrass();
        backGrass = scroller.getBackGrass();
        pipe1 = scroller.getPipe1();
        pipe2 = scroller.getPipe2();
        pipe3 = scroller.getPipe3();
    }

    private void initAssets(){
        bg = AssetLoader.bg;
        grass = AssetLoader.grass;
        birdAnimation = AssetLoader.birdAnimation;
        birdMid = AssetLoader.birdMid;
        birdDown = AssetLoader.birdDown;
        birdUp = AssetLoader.birdUp;
        skullUp = AssetLoader.skullUp;
        skullDown = AssetLoader.skullDown;
        bar = AssetLoader.bar;
    }

    private void drawGrass(){
        batcher.draw(grass, frontGrass.getX(), frontGrass.getY(),
                frontGrass.getWidth(), frontGrass.getHeight());
        batcher.draw(grass, backGrass.getX(), backGrass.getY(),
                backGrass.getWidth(), backGrass.getHeight());
    }

    private void drawSkulls() {

        batcher.draw(skullUp, pipe1.getX() - 1,
                pipe1.getY() + pipe1.getHeight() - 14, 24, 14);
        batcher.draw(skullDown, pipe1.getX() - 1,
                pipe1.getY() + pipe1.getHeight() + 45, 24, 14);

        batcher.draw(skullUp, pipe2.getX() - 1,
                pipe2.getY() + pipe2.getHeight() - 14, 24, 14);
        batcher.draw(skullDown, pipe2.getX() - 1,
                pipe2.getY() + pipe2.getHeight() + 45, 24, 14);

        batcher.draw(skullUp, pipe3.getX() - 1,
                pipe3.getY() + pipe3.getHeight() - 14, 24, 14);
        batcher.draw(skullDown, pipe3.getX() - 1,
                pipe3.getY() + pipe3.getHeight() + 45, 24, 14);
    }

    private void drawPipes() {
        batcher.draw(bar, pipe1.getX(), pipe1.getY(), pipe1.getWidth(),
                pipe1.getHeight());
        batcher.draw(bar, pipe1.getX(), pipe1.getY() + pipe1.getHeight() + 45,
                pipe1.getWidth(), midPointY + 66 - (pipe1.getHeight() + 45));

        batcher.draw(bar, pipe2.getX(), pipe2.getY(), pipe2.getWidth(),
                pipe2.getHeight());
        batcher.draw(bar, pipe2.getX(), pipe2.getY() + pipe2.getHeight() + 45,
                pipe2.getWidth(), midPointY + 66 - (pipe2.getHeight() + 45));

        batcher.draw(bar, pipe3.getX(), pipe3.getY(), pipe3.getWidth(),
                pipe3.getHeight());
        batcher.draw(bar, pipe3.getX(), pipe3.getY() + pipe3.getHeight() + 45,
                pipe3.getWidth(), midPointY + 66 - (pipe3.getHeight() + 45));
    }

    public void render(float runTime){
        //setting our background black - prevents flickering
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeType.Filled);

        // drawing background color
        shapeRenderer.setColor(55/255.0f, 80/255.0f, 100/255.0f, 1);
        shapeRenderer.rect(0, 0, 136, midPointY + 66);

        // grass
        shapeRenderer.setColor(111/255.0f, 186/255.0f, 45/255.0f, 1);
        shapeRenderer.rect(0, midPointY + 66, 136, 11);

        // dirt
        shapeRenderer.setColor(147/255.0f, 80/255.0f, 27/255.0f, 1);
        shapeRenderer.rect(0, midPointY+77, 136, 52);

        shapeRenderer.end();

        batcher.begin();
        // background doesn't need transparency, so we turn it off
        // good for performance
        batcher.disableBlending();
        batcher.draw(bg, 0, midPointY+23, 136, 43);


        drawGrass();
        drawPipes();

        // bird needs transparency, so let's turn it back on
        batcher.enableBlending();
        drawSkulls();

        if(myBird.shouldntFlap()){
            batcher.draw(birdMid,
                    myBird.getX(), myBird.getY(),
                    myBird.getWidth() / 2.0f, myBird.getHeight() / 2.0f,
                    myBird.getWidth(), myBird.getHeight(),
                    1, 1, myBird.getRotations()
            );
        }else{
            batcher.draw((TextureRegion)birdAnimation.getKeyFrame(runTime),
                    myBird.getX(), myBird.getY(),
                    myBird.getWidth() / 2.0f,myBird.getHeight() / 2.0f,
                    myBird.getWidth(), myBird.getHeight(),
                    1, 1, myBird.getRotations()
            );
        }

        // displaying ready screen/bird
        if(myWorld.isReady()){
            AssetLoader.shadow.draw(batcher, "Touch to start!",
                    (136/2) - 42, 76);

            AssetLoader.font.draw(batcher, "Touch to start!",
                    (136/2) - 70, 76);
        }else{
            if(myWorld.isGameOver()) {
                AssetLoader.shadow.draw(batcher, "Game Over", 25, 56);
                AssetLoader.font.draw(batcher, "Game Over", 25, 56);

                AssetLoader.shadow.draw(batcher, "Try Again?", 23, 76);
                AssetLoader.font.draw(batcher, "Try Again?", 23, 76);
            }

            String score = myWorld.getScore() + "";

            // draw shadow first
            AssetLoader.shadow.draw(batcher, "" + myWorld.getScore(), (136 / 2) - (3 * score.length()), 12);

            // font afterwards - like layering
            AssetLoader.font.draw(batcher, "" + myWorld.getScore(), 136 / 2 - 3 * score.length(), 12);
        }
        batcher.end();
    }
}
