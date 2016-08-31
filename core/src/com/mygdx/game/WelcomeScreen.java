package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by lingu on 2016/8/30.
 */
public class WelcomeScreen extends ScreenAdapter {
    WonderfulColor game;
    OrthographicCamera guiCam;
    TextureRegion angleRegion;
    Timer timer;
    float time;
    Stage stage;

    public WelcomeScreen(WonderfulColor game) {
        this.game = game;
        timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                Assets.animatedImage.remove();
                for (int i = 0; i < 15; i++) {
                    Assets.textureFlipRegions[i].flip(true, false);
                }
                Assets.longTextImage.remove();
                for (int i = 0; i < 5; i++) {
                    Assets.textImage[i].remove();
                }
                Assets.welcomeStage.addActor(Assets.animatedFlipImage);
                AnimationResource animationResource = new AnimationResource("res/pipi.atlas"
                        , 25, 0.15f, true);
                AnimatedImage animatedImage = animationResource.createAnimatedImage();
                animatedImage.setPosition(50, 50);
                Assets.welcomeStage.addActor(animatedImage);
            }
        }, 6);
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
