package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
    public static TextureAtlas angleAtlas;
    public static TextureAtlas textAtlas;

    public static TextureRegion[] textureRegions;
    public static TextureRegion longTextRegion;
    public static Image longTextImage;


    public static Stage welcomeStage;
    public static Image textImage;
    public static Image backgroundImage;
    public static AnimatedImage animatedImage;

    public static Texture loadTexture(String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void load() {
        background = loadTexture("res/welcome.png");
        backgroundRegion = new TextureRegion(background, 0, 0, 1280, 800);
        backgroundImage = new Image(backgroundRegion);
        angleAtlas = new TextureAtlas(Gdx.files.internal("res/big.atlas"));
        if (angleAtlas != null) {
            textureRegions = new TextureRegion[33];
            for (int i = 0; i < 33; i++) {
                if (i < 9) textureRegions[i] = angleAtlas.findRegion("000" + (i + 1));
                else textureRegions[i] = angleAtlas.findRegion("00" + (i + 1));
            }
            welcomeStage = new Stage(new ScalingViewport(Scaling.stretch, 1280, 800, new OrthographicCamera()));
            backgroundImage.setBounds(0, 0, 1280, 800);
            welcomeStage.addActor(backgroundImage);
            angleAnimation = new Animation(0.2f, textureRegions);
            animatedImage = new AnimatedImage(angleAnimation);
            animatedImage.setPosition(10, 400);
            welcomeStage.addActor(animatedImage);
        }
        textAtlas = new TextureAtlas(Gdx.files.internal("res/text.atlas"));
        longTextRegion = textAtlas.findRegion("long_text");
        longTextImage = new Image(longTextRegion);
        longTextImage.setPosition(400,530);
        longTextImage.addAction(Actions.sequence(Actions.alpha(0),Actions.delay(4f),Actions.fadeIn(1f)));
        welcomeStage.addActor(longTextImage);
        String[] str = {"qi", "miao", "de", "yan", "se"};
        int[] heights = {650, 700, 750, 740, 730};
        if (textAtlas != null) {
            TextureRegion textureRegion;
            for (int i = 0; i < str.length; i++) {
                textureRegion = textAtlas.findRegion(str[i]);
                textImage = new Image(textureRegion);
                textImage.setPosition(400 + i * 100, 600);
                textImage.addAction(Actions.sequence(Actions.delay(i * 0.6f), Actions.moveTo((400 + i * 100), heights[i], 1),
                        Actions.moveTo(400 + i * 100, 600, 0.5f)));
                welcomeStage.addActor(textImage);
            }
        }
    }
}
