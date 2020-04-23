package com.kilobolt.gameworld;

import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameRenderer {
    GameWorld myWorld;

    private OrthographicCamera camera;

    private ShapeRenderer shapeRenderer;

    public GameRenderer(GameWorld world){
        myWorld = world;

        camera = new OrthographicCamera();
        camera.setToOrtho(true, 136, 204);

        // helps us draw shapes/lines
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    public void render(){
        Gdx.app.log("GameRenderer", "rendering");

        //setting our background black - prevents flickering
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // drawing filled shapes with color 87,109,120 (RGB respectively)
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(87 / 255.0f, 109 / 255.0f, 120 / 255.0f, 1);

        //creating our rectangle using the object created in myWorld
        shapeRenderer.rect(myWorld.getRect().x, myWorld.getRect().y,
                myWorld.getRect().width, myWorld.getRect().height);

        //must close shapeRenderer when finished
        shapeRenderer.end();

        //setting shapes to be empty filled with border lines
        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setColor(255/255.0f, 109/255.0f, 120/255.0f, 1);

        //again creating our rectangle using the object in myWorld
        shapeRenderer.rect(myWorld.getRect().x, myWorld.getRect().y,
                myWorld.getRect().width, myWorld.getRect().height);

        //must close shapeRenderer when finished
        shapeRenderer.end();
    }
}
