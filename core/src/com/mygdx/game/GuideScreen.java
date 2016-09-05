package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by lingu on 2016/8/31.
 */
public class GuideScreen extends ScreenAdapter {
    WonderfulColor game;
    float time;
    Music angleSpeak03;
    private Button btnSanjianse;
    private Button btnRGB;
    AnimatedImage fingerAnimatedImage;
    Music fingerMusic;
    public GuideScreen(WonderfulColor game) {
        this.game = game;
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

    void initWelcomeStage() {
        Assets.pipiNoSpeakAnimatedImage.remove();
        Assets.canvasImage.remove();
        Assets.blueImage.remove();
        Assets.redImage.remove();
        Assets.yellowImage.remove();
        Assets.welcomeStage.getRoot().getColor().a = 1;
        ImageResource red = new ImageResource(Assets.textAtlas, "red_big", 200, 100);
        Image redImage = red.createImage();
        redImage.addAction(Actions.sequence(Actions.delay(2f), Actions.scaleTo(2f, 2f, 0.1f)
                , Actions.scaleTo(1f, 1f, 0.1f), Actions.scaleTo(1.5f, 1.5f, 0.1f)
                , Actions.scaleTo(1f, 1f, 0.1f)));
        Assets.welcomeStage.addActor(redImage);
        ImageResource yellow = new ImageResource(Assets.textAtlas, "yellow_big", 400, 70);
        Image yellowImage = yellow.createImage();
        yellowImage.addAction(Actions.sequence(Actions.delay(2.5f), Actions.scaleTo(2f, 2f, 0.1f)
                , Actions.scaleTo(1f, 1f, 0.1f), Actions.scaleTo(1.5f, 1.5f, 0.1f)
                , Actions.scaleTo(1f, 1f, 0.1f)));
        Assets.welcomeStage.addActor(yellowImage);
        ImageResource blue = new ImageResource(Assets.textAtlas, "blue_big", 600, 100);
        Image blueImage = blue.createImage();
        blueImage.addAction(Actions.sequence(Actions.delay(3f), Actions.scaleTo(2f, 2f, 0.1f)
                , Actions.scaleTo(1f, 1f, 0.1f), Actions.scaleTo(1.5f, 1.5f, 0.1f)
                , Actions.scaleTo(1f, 1f, 0.1f)));
        Assets.welcomeStage.addActor(blueImage);
        angleSpeak03 = Gdx.audio.newMusic(Gdx.files.internal("res/angle03.mp3"));
        angleSpeak03.play();
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                ImageResource btnSanyuanseResource = new ImageResource(Assets.textAtlas, "btn_sanyuan_normal"
                        , "btn_sanyuan_select", 0, 400, new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                        super.clicked(event, x, y);
                    }
                });
                final Music notRight = Gdx.audio.newMusic(Gdx.files.internal("res/not_right.mp3"));
                btnRGB=btnSanyuanseResource.createButton();
                Assets.welcomeStage.addActor(btnRGB);
                ImageResource btnSanjianseResource = new ImageResource(Assets.textAtlas, "btn_sanjian_normal"
                        , "btn_sanjian_select", 475, 400, new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (!fingerMusic.isPlaying())
                            notRight.play();
                        btnSanjianse.addAction(Actions.fadeOut(0.3f));
                        Assets.welcomeStage.addActor(btnSanjianse);
                        btnSanjianse.remove();
                        btnRGB.addAction(Actions.moveTo(300,400,0.5f));
                        Assets.welcomeStage.addActor(btnRGB);
                        fingerAnimatedImage.remove();

                    }
                });
                btnSanjianse=btnSanjianseResource.createButton();
                Assets.welcomeStage.addActor(btnSanjianse);
                AnimationResource animationResource = new AnimationResource("res/finger.atlas", 51, 0.1f, true);

                fingerAnimatedImage = animationResource.createAnimatedImage();
                fingerAnimatedImage.setPosition(300, 330);
                Assets.welcomeStage.addActor(fingerAnimatedImage);

            }
        }, 7f);
        angleSpeak03.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                 fingerMusic = Gdx.audio.newMusic(Gdx.files.internal("res/finger.mp3"));
                fingerMusic.play();

            }
        });

    }
}
