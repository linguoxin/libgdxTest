package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ViewportDemo extends ApplicationAdapter implements InputProcessor {
    SpriteBatch batch;
    Sprite aspectRatios;
    OrthographicCamera camera;
    Viewport viewport;

    @Override
    public void create () {
        batch = new SpriteBatch();
        aspectRatios = new Sprite(new Texture(Gdx.files.internal("Aspect.jpg")));
        aspectRatios.setPosition(0,0);
        aspectRatios.setSize(100,100);

        camera = new OrthographicCamera();
        viewport = new FillViewport(100,100,camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void render () {

        camera.update();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        aspectRatios.draw(batch);
        batch.end();
    }

    @Override
    public void dispose(){
        aspectRatios.getTexture().dispose();
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width,height);
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Gdx.app.log("Mouse Event","Click at " + screenX + "," + screenY);
        Vector3 worldCoordinates = camera.unproject(new Vector3(screenX,screenY,0));
        Gdx.app.log("Mouse Event","Projected at " + worldCoordinates.x + "," + worldCoordinates.y);
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