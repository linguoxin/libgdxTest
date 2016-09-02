package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;


/**
 * Created by lingu on 2016/8/30.
 */
public class Assets {
    public static Texture background;
    public static TextureRegion backgroundRegion;
    public static Animation angleAnimation;
    public static Animation angleFlipAnimation;
    public static Animation angleNoSpeakAnimation;
    public static Animation angleFlipNoSpeakAnimation;
    public static Animation pipiAnimation;
    public static Animation pipiFlipAnimation;
    public static TextureAtlas pipiAtlas;

    public static TextureAtlas angleAtlas;
    public static TextureAtlas textAtlas;
    public static TextureRegion[] pipiRegions;

    public static TextureRegion[] textureRegions;
    public static TextureRegion[] textureFlipRegions;
    public static TextureRegion[] textureNoSpeakRegions;
    public static TextureRegion[] textureFlipNoSpeakRegions;
    public static TextureRegion longTextRegion;
    public static Image canvasImage;
    public static Image longTextImage;
    public static Image topFlowerImage;
    public static Image bottomFlowerImage;
    public static TextureRegion canvasRegion;
    public static TextureRegion topFlowerRegion;
    public static TextureRegion bottomFlowerRegion;
    public static TextureRegion closeNormalRegion;
    public static TextureRegion closeDownRegion;
    public static TextureRegionDrawable closeNormalRegionDrawable;
    public static TextureRegionDrawable closeDownRegionDrawable;
    public static Button closeButton;
    public static Music welcomeMusic;
    public static Music pipiMusic;

    public static Stage welcomeStage;
    public static Image[] textImage;
    public static Image backgroundImage;
    public static AnimatedImage animatedImage;
    public static AnimatedImage animatedFlipImage;
    public static AnimatedImage animatedFlipNoSpeakImage;
    public static AnimatedImage pipiNoSpeakAnimatedImage;

    public static AnimatedImage pipiAnimatedImage;
    public static Image redImage;
    public static Image yellowImage;
    public static Image blueImage;

    public static Texture loadTexture(String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void load() {
        background = loadTexture("res/welcome.png");
        backgroundRegion = new TextureRegion(background, 0, 0, 1280, 800);
        backgroundImage = new Image(backgroundRegion);
        angleAtlas = new TextureAtlas(Gdx.files.internal("res/big.atlas"));
        if (angleAtlas != null) {
            textureRegions = new TextureRegion[15];
            textureNoSpeakRegions = new TextureRegion[18];

            textureFlipRegions = new TextureRegion[15];
            textureFlipNoSpeakRegions = new TextureRegion[18];

            for (int i = 0; i < 33; i++) {
                if (i <= 14) {
                    if (i < 9) textureRegions[i] = angleAtlas.findRegion("000" + (i + 1));
                    else textureRegions[i] = angleAtlas.findRegion("00" + (i + 1));
                    textureFlipRegions[i] = textureRegions[i];
                } else {
                    textureFlipNoSpeakRegions[i - 15] = textureNoSpeakRegions[i - 15] = angleAtlas.findRegion("00" + (i + 1));

                }
            }
            welcomeStage = new Stage(new ScalingViewport(Scaling.stretch, 1280, 800, new OrthographicCamera()));
            backgroundImage.setBounds(0, 0, 1280, 800);
            welcomeStage.addActor(backgroundImage);
            angleAnimation = new Animation(0.15f, textureRegions);
            animatedImage = new AnimatedImage(angleAnimation);
            animatedImage.setPosition(10, 400);
            welcomeStage.addActor(animatedImage);
            angleFlipAnimation = new Animation(0.15f, textureFlipRegions);
            animatedFlipImage = new AnimatedImage(angleFlipAnimation);
            animatedFlipImage.setPosition(960, 250);
            angleFlipNoSpeakAnimation = new Animation(0.15f, textureFlipNoSpeakRegions);
            animatedFlipNoSpeakImage = new AnimatedImage(angleFlipNoSpeakAnimation);
            animatedFlipNoSpeakImage.setPosition(960, 250);

        }
        textAtlas = new TextureAtlas(Gdx.files.internal("res/text.atlas"));
        closeNormalRegion = textAtlas.findRegion("close_normal");
        closeDownRegion = textAtlas.findRegion("close_down");
        closeNormalRegionDrawable = new TextureRegionDrawable(closeNormalRegion);
        closeDownRegionDrawable = new TextureRegionDrawable(closeDownRegion);
        closeButton = new Button(closeNormalRegionDrawable, closeDownRegionDrawable);
        closeButton.setPosition(1200, 710);
        closeButton.addListener(new ClickListener() {
                                    @Override
                                    public void clicked(InputEvent event, float x, float y) {
                                        Gdx.app.exit();
                                        super.clicked(event, x, y);
                                    }
                                }
        );
        welcomeStage.addActor(closeButton);

        longTextRegion = textAtlas.findRegion("long_text");
        longTextImage = new Image(longTextRegion);
        longTextImage.setPosition(400, 530);
        longTextImage.addAction(Actions.sequence(Actions.alpha(0), Actions.delay(4f), Actions.fadeIn(1f)));
        welcomeStage.addActor(longTextImage);
        topFlowerRegion = textAtlas.findRegion("top_flower");
        topFlowerImage = new Image(topFlowerRegion);
        topFlowerImage.setPosition(-30, 580);
        topFlowerImage.addAction(Actions.sequence(Actions.alpha(0), Actions.delay(5.5f), Actions.fadeIn(0.1f)));
        welcomeStage.addActor(topFlowerImage);
        bottomFlowerRegion = textAtlas.findRegion("bottom_flower");
        bottomFlowerImage = new Image(bottomFlowerRegion);
        bottomFlowerImage.setPosition(-30, 0);
        bottomFlowerImage.addAction(Actions.sequence(Actions.alpha(0), Actions.delay(5.5f), Actions.fadeIn(0.1f)));
        welcomeStage.addActor(bottomFlowerImage);
        canvasRegion = textAtlas.findRegion("canvas");
        canvasImage = new Image(canvasRegion);
        canvasImage.setPosition(300, 160);
        String[] str = {"qi", "miao", "de", "yan", "se"};
        int[] heights = {650, 700, 750, 740, 730};
        if (textAtlas != null) {
            TextureRegion textureRegion;
            textImage = new Image[str.length];
            for (int i = 0; i < str.length; i++) {
                textureRegion = textAtlas.findRegion(str[i]);
                textImage[i] = new Image(textureRegion);
                textImage[i].setPosition(400 + i * 100, 600);
                textImage[i].addAction(Actions.sequence(Actions.delay(i * 0.3f), Actions.moveTo((400 + i * 100), heights[i], 1),
                        Actions.moveTo(400 + i * 100, 600, 0.5f)));
                welcomeStage.addActor(textImage[i]);
            }
        }
        welcomeMusic = Gdx.audio.newMusic(Gdx.files.internal("res/welcome.mp3"));
        welcomeMusic.play();
        Gdx.input.setInputProcessor(welcomeStage);
    }
    public static void initPipi() {
        pipiAtlas = new TextureAtlas(Gdx.files.internal("res/pipi.atlas"));
        if (pipiAtlas != null) {
            pipiRegions = new TextureRegion[24];
            String name = "";
            for (int i = 0; i < 24; i++) {
                name = "0000 (" + (i + 1) + ")";
                pipiRegions[i] = pipiAtlas.findRegion(name);
                if (pipiRegions[i] == null) Gdx.app.error("lgx", "ipiRegions[i]==null i=" + i
                        + " name=" + name);
            }
            pipiAnimation = new Animation(0.15f, pipiRegions);
            pipiAnimatedImage = new AnimatedImage(pipiAnimation);
            pipiAnimatedImage.setPosition(30, 40);
            welcomeStage.addActor(pipiAnimatedImage);
        }

    }

}
