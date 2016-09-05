package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by lingu on 2016/9/1.
 */
public class ImageResource {
    TextureAtlas textureAtlas;
    Image image;
    float x, y;
    String name;
    String normal;
    String select;
    Button button;
    ClickListener clickListener;

    public ImageResource(TextureAtlas textureAtlas, String name, float x, float y) {
        this.textureAtlas = textureAtlas;
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public ImageResource(TextureAtlas textureAtlas, String normal, String select, float x, float y, ClickListener clickListener) {
        this.textureAtlas = textureAtlas;
        this.normal = normal;
        this.select = select;
        this.x = x;
        this.y = y;
        this.clickListener = clickListener;
    }

    public Image createImage() {
        if (image == null) {
            TextureRegion textureRegion;
            try {
                textureRegion = textureAtlas.findRegion(name);
            } catch (Exception e) {
                Gdx.app.error("libgdx", "the texture can not found ,name=" + name);
                return null;
            }
            image = new Image(textureRegion);
            image.setPosition(x, y);
        }
        return image;
    }

    public Button createButton() {
        if (button == null) {
            TextureRegion normalRegion = textureAtlas.findRegion(normal);
            TextureRegion selectRegion = textureAtlas.findRegion(select);
            TextureRegionDrawable normalRegionDrawable = new TextureRegionDrawable(normalRegion);
            TextureRegionDrawable selectRegionDrawable = new TextureRegionDrawable(selectRegion);
            button = new Button(normalRegionDrawable, selectRegionDrawable);
            if(clickListener!=null)
            button.addListener(clickListener);
            button.setPosition(x, y);

        }
        return button;
    }
}
