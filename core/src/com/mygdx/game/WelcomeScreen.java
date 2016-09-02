package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
    AnimatedImage pipiSpeakAnimatedImage;

    public WelcomeScreen(final WonderfulColor game) {
        this.game = game;
        timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                Assets.animatedImage.remove();
                for (int i = 0; i < 18; i++) {
                    Assets.textureFlipNoSpeakRegions[i].flip(true, false);
                }
                Assets.longTextImage.remove();
                for (int i = 0; i < 5; i++) {
                    Assets.textImage[i].remove();
                }
                Assets.welcomeStage.addActor(Assets.animatedFlipNoSpeakImage);
                AnimationResource animationResource = new AnimationResource("res/pipi_speak.atlas"
                        , 24, 0.15f, true);
                Music pipi = Gdx.audio.newMusic(Gdx.files.internal("res/pipi01.mp3"));
                pipi.play();
                pipi.setOnCompletionListener(new Music.OnCompletionListener() {
                    @Override
                    public void onCompletion(Music music) {
                        if (pipiSpeakAnimatedImage != null) pipiSpeakAnimatedImage.remove();
                        AnimationResource animationResource = new AnimationResource("res/pipi_no_speak.atlas"
                                , 26, 0.15f, true);
                        Assets.pipiNoSpeakAnimatedImage = animationResource.createAnimatedImage();
                        Assets.pipiNoSpeakAnimatedImage.setPosition(120, 150);
                        Assets.welcomeStage.addActor(Assets.pipiNoSpeakAnimatedImage);
                        Assets.animatedFlipNoSpeakImage.remove();
                        for (int i = 0; i < 15; i++) {
                            Assets.textureFlipRegions[i].flip(true, false);

                        }
                        Assets.welcomeStage.addActor(Assets.animatedFlipImage);
                        Music angle = Gdx.audio.newMusic(Gdx.files.internal("res/angle02.mp3"));
                        angle.play();
                        angle.setOnCompletionListener(new Music.OnCompletionListener() {
                            @Override
                            public void onCompletion(Music music) {
                             game.switchScreen(Assets.welcomeStage,game,new GuideScreen(game));
                            }
                        });

                    }
                });
                pipiSpeakAnimatedImage = animationResource.createAnimatedImage();
                pipiSpeakAnimatedImage.setPosition(120, 150);
                Assets.welcomeStage.addActor(pipiSpeakAnimatedImage);
                AnimationResource beeResource = new AnimationResource("res/bee.atlas"
                        , 7, 0.15f, true);
                AnimatedImage beeImage1;
                AnimatedImage beeImage2;
                AnimatedImage image = beeResource.createAnimatedImage();
                beeImage1 = image;
                beeImage2 = beeResource.newAnimatedImage();
                beeImage1.setPosition(300, 50);
                beeImage2.setPosition(800, 400);
                beeImage2.setSize(50, 50);
                Assets.welcomeStage.addActor(beeImage1);
                Assets.welcomeStage.addActor(beeImage2);
                Assets.welcomeStage.addActor(Assets.canvasImage);
                ImageResource red = new ImageResource(Assets.textAtlas, "red", 300, 50);
                Assets.redImage = red.createImage();
                Assets.redImage.addAction(Actions.sequence(Actions.delay(4.5f), Actions.scaleTo(2f, 2f, 0.1f)
                        , Actions.scaleTo(1f, 1f, 0.1f), Actions.scaleTo(1.5f, 1.5f, 0.1f)
                        , Actions.scaleTo(1f, 1f, 0.1f)));
                Assets.welcomeStage.addActor(Assets.redImage);
                ImageResource yellow = new ImageResource(Assets.textAtlas, "yellow", 390, 20);
                Assets.yellowImage = yellow.createImage();
                Assets.yellowImage.addAction(Actions.sequence(Actions.delay(5f), Actions.scaleTo(2f, 2f, 0.1f)
                        , Actions.scaleTo(1f, 1f, 0.1f), Actions.scaleTo(1.5f, 1.5f, 0.1f)
                        , Actions.scaleTo(1f, 1f, 0.1f)));
                Assets.welcomeStage.addActor(Assets.yellowImage);
                ImageResource blue = new ImageResource(Assets.textAtlas, "blue", 490, 50);
                Assets.blueImage = blue.createImage();
                Assets.blueImage.addAction(Actions.sequence(Actions.delay(5.5f), Actions.scaleTo(2f, 2f, 0.1f)
                        , Actions.scaleTo(1f, 1f, 0.1f), Actions.scaleTo(1.5f, 1.5f, 0.1f)
                        , Actions.scaleTo(1f, 1f, 0.1f)));
                Assets.welcomeStage.addActor(Assets.blueImage);
            }
        }, 6);
    }

    public void draw(float delta) {
        time += delta;
        GL20 gl = Gdx.gl;
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
