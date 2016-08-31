package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

/**
 * Created by lingu on 2016/8/30.
 */
public class WelcomeScreen extends ScreenAdapter {
    WonderfulColor game;
    OrthographicCamera guiCam;
    TextureRegion angleRegion;
    float time;
    Stage stage;
    public WelcomeScreen(WonderfulColor game) {
        this.game = game;

    }

    public void draw(float delta) {
        time += delta;
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
     //   game.spriteBatch.setProjectionMatrix(guiCam.combined);
        Assets.welcomeStage.act();
        Assets.welcomeStage.draw();
        Assets.animatedImage.act(delta);


        // guiCam.update();
        // game.spriteBatch.disableBlending();
//        //game.spriteBatch.draw(Assets.backgroundRegion, 0, 0, 1280, 800);
//        //angleRegion = Assets.angleAnimation.getKeyFrame(time, Animation.ANIMATION_LOOPING);
//        if (angleRegion != null)
//            game.spriteBatch.draw(angleRegion, 120, 200);
    }

    @Override
    public void render(float delta) {
        draw(delta);
    }
}
