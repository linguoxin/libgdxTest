package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CameraDemo2 extends ApplicationAdapter implements InputProcessor {
    SpriteBatch batch;
    Sprite theWorld;
    OrthographicCamera camera;

    final float WORLD_WIDTH = 50;
    final float WORLD_HEIGHT = 25;

    @Override
    public void create () {
        batch = new SpriteBatch();
        theWorld = new Sprite(new Texture(Gdx.files.internal("TheWorld.png")));
        theWorld.setPosition(0,0);
        theWorld.setSize(50,25);

        float aspectRatio = (float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();

        camera = new OrthographicCamera(25 * aspectRatio ,25);
        camera.position.set(WORLD_WIDTH/2,WORLD_HEIGHT/2,0);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        theWorld.draw(batch);
        batch.end();
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.RIGHT)
            camera.translate(1f,0f);
        if(keycode == Input.Keys.LEFT)
            camera.translate(-1f,0f);
        if(keycode == Input.Keys.UP)
            camera.translate(0f,1f);
        if(keycode == Input.Keys.DOWN)
            camera.translate(0f,-1f);

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}