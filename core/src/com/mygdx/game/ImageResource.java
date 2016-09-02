package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by lingu on 2016/9/1.
 */
public class ImageResource {
    TextureAtlas textureAtlas;
    Image image;
    float x, y;
    String name;
    public ImageResource(TextureAtlas textureAtlas,String name, float x, float y) {
        this.textureAtlas = textureAtlas;
        this.name=name;
        this.x = x;
        this.y = y;
    }

    public Image createImage() {
        if (image == null) {
            TextureRegion textureRegion;
            try {
                textureRegion = textureAtlas.findRegion(name);
            }catch (Exception e){
                Gdx.app.error("libgdx","the texture can not found ,name="+name);
                return null;
            }
            image=new Image(textureRegion);
            image.setPosition(x,y);
        }
        return image;
    }
}
