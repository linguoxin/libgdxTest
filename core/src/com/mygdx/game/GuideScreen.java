package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by lingu on 2016/8/31.
 */
public class GuideScreen extends ScreenAdapter {
    WonderfulColor game;
    float time;

    public GuideScreen(WonderfulColor game){
        this.game=game;
        initWelcomeStage();
    }

    @Override
    public void render(float delta) {
        draw(delta);

    }
    public void draw(float delta) {
        time += delta;
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Assets.welcomeStage.act();
        Assets.welcomeStage.draw();
        Assets.animatedImage.act(delta);


    }
    void initWelcomeStage(){
     Assets.pipiNoSpeakAnimatedImage.remove();
        Assets.canvasImage.remove();
        Assets.blueImage.remove();
        Assets.redImage.remove();
        Assets.yellowImage.remove();
        Assets.welcomeStage.getRoot().getColor().a = 1;
        ImageResource red = new ImageResource(Assets.textAtlas, "red_big", 200, 100);
        Image redImage = red.createImage();
        redImage.addAction(Actions.sequence(Actions.delay(4.5f), Actions.scaleTo(2f, 2f, 0.1f)
                , Actions.scaleTo(1f, 1f, 0.1f), Actions.scaleTo(1.5f, 1.5f, 0.1f)
                , Actions.scaleTo(1f, 1f, 0.1f)));
        Assets.welcomeStage.addActor(redImage);
        ImageResource yellow = new ImageResource(Assets.textAtlas, "yellow_big", 400, 70);
        Image yellowImage = yellow.createImage();
        yellowImage.addAction(Actions.sequence(Actions.delay(5f), Actions.scaleTo(2f, 2f, 0.1f)
                , Actions.scaleTo(1f, 1f, 0.1f), Actions.scaleTo(1.5f, 1.5f, 0.1f)
                , Actions.scaleTo(1f, 1f, 0.1f)));
        Assets.welcomeStage.addActor(yellowImage);
        ImageResource blue = new ImageResource(Assets.textAtlas, "blue_big", 600, 100);
        Image blueImage = blue.createImage();
        blueImage.addAction(Actions.sequence(Actions.delay(5.5f), Actions.scaleTo(2f, 2f, 0.1f)
                , Actions.scaleTo(1f, 1f, 0.1f), Actions.scaleTo(1.5f, 1.5f, 0.1f)
                , Actions.scaleTo(1f, 1f, 0.1f)));
        Assets.welcomeStage.addActor(blueImage);
    }
}
