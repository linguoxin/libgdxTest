package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by lingu on 2016/8/30.
 */
public class WonderfulColor extends Game {
    public SpriteBatch spriteBatch;
    public float WIDTH = 1280;
    public float HEIGHT = 800;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        Assets.load();
        setScreen(new WelcomeScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }
}
