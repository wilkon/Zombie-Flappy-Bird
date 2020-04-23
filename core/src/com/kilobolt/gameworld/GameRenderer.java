package com.kilobolt.gameworld;

import com.badlogic.gdx.Gdx;
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

        // bird needs transparency, so let's turn it back on
        batcher.enableBlending();
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

        batcher.end();
    }
}
