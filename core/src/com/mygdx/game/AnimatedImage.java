package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class AnimatedImage extends Image
{
    protected Animation animation = null;
    private float stateTime = 0;

    public AnimatedImage(Animation animation) {
        super(animation.getKeyFrame(0,0));
        this.animation = animation;
    }

    @Override
    public void act(float delta)
    {
        ((TextureRegionDrawable)getDrawable()).setRegion(animation.getKeyFrame(stateTime+=delta,0));
        super.act(delta);
    }
}