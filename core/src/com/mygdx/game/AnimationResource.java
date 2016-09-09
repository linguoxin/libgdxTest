package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by lingu on 2016/8/31.
 */
public class AnimationResource {
    String atlasPath;
    int length;
    AnimatedImage animatedImage = null;
    TextureRegion[] textureRegions;
    TextureRegion[] flipTextureRegions;

    float frameDuration = 0.1f;
    boolean isLoop;

    public AnimationResource(String atlasPath, int length, float frameDuration, boolean isLoop) {
        this.atlasPath = atlasPath;
        this.length = length;
        textureRegions = new TextureRegion[length];
        flipTextureRegions = new TextureRegion[length];

        this.frameDuration = frameDuration;
        this.isLoop = isLoop;
    }

    public AnimatedImage createAnimatedImage() {
        if (animatedImage == null) {
            TextureAtlas atlas = null;
            try {
                atlas = new TextureAtlas(Gdx.files.internal(atlasPath));
            } catch (Exception e) {
                Gdx.app.error("libgdx", "can not load atlasPath=" + atlasPath);
            }
            if (atlas != null) {
                String pictureName = "";
                for (int i = 0; i < length; i++) {
                    if (i < 10) {
                        pictureName = "0000" + i;
                    } else pictureName = "000" + i;
                    textureRegions[i] = atlas.findRegion(pictureName);
                }
                Animation animation = new Animation(frameDuration, textureRegions);
                animatedImage = new AnimatedImage(animation, isLoop);
                return animatedImage;
            }

        } else
            return animatedImage;
        return null;
    }
    public AnimatedImage createFilpAnimatedImage() {
        if (animatedImage == null) {
            TextureAtlas atlas = null;
            try {
                atlas = new TextureAtlas(Gdx.files.internal(atlasPath));
            } catch (Exception e) {
                Gdx.app.error("libgdx", "can not load atlasPath=" + atlasPath);
            }
            if (atlas != null) {
                String pictureName = "";
                for (int i = 0; i < length; i++) {
                    if (i < 10) {
                        pictureName = "0000" + i;
                    } else pictureName = "000" + i;
                    flipTextureRegions[i]=textureRegions[i] = atlas.findRegion(pictureName);
                }
                for (int i=0;i<flipTextureRegions.length;i++)
                    flipTextureRegions[i].flip(true,false);
                Animation animation = new Animation(frameDuration, flipTextureRegions);
                animatedImage = new AnimatedImage(animation, isLoop);
                return animatedImage;
            }

        } else
            return animatedImage;
        return null;
    }
    public AnimatedImage newAnimatedImage(){
        Animation animation = new Animation(frameDuration, textureRegions);
        AnimatedImage animatedImage=new AnimatedImage(animation,isLoop);
        return animatedImage;
    }
}

