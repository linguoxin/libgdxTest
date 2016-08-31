package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class AnimatedImage extends Image {
    protected Animation animation = null;
    private float stateTime = 0;
    private int mode = 0;//0---loop,1---once;

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
        ((TextureRegionDrawable) getDrawable()).setRegion(animation.getKeyFrame(stateTime += delta, mode));
        super.act(delta);
    }
}