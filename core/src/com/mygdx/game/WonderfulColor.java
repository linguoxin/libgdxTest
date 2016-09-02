package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

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
    public void switchScreen(Stage currentStage, final Game game, final Screen newScreen){
        currentStage.getRoot().getColor().a = 1;
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.fadeOut(0.5f));
        sequenceAction.addAction(Actions.run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(newScreen);
            }
        }));
        sequenceAction.addAction(Actions.delay(0.5f));
        sequenceAction.addAction(Actions.fadeIn(0f));

        currentStage.getRoot().addAction(sequenceAction);
    }
}
