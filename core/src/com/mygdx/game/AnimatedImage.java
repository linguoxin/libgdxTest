package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class AnimatedImage extends Image {
    protected Animation animation = null;
    private float stateTime = 0;
    private int mode = 0;//0---loop,1---once;
    private TextureRegion textureRegion;
    private boolean isStop;

    private int index = 0;

    public AnimatedImage(Animation animation) {
        super(animation.getKeyFrame(0, 0));
        this.animation = animation;
    }

    public AnimatedImage(Animation animation, boolean isLoop) {
        super(animation.getKeyFrame(0, 0));
        mode = isLoop ? Animation.ANIMATION_LOOPING : Animation.ANIMATION_NONLOOPING;
        this.animation = animation;
    }

    @Override
    public void act(float delta) {
        if (isStop) textureRegion = animation.getIndexFrame(index);
        else
            textureRegion = animation.getKeyFrame(stateTime += delta, mode);
        if (textureRegion != null)
            ((TextureRegionDrawable) getDrawable()).setRegion(textureRegion);
        super.act(delta);
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    public void setStopIndex(int index) {
        this.index = index;
    }

}