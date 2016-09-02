package com.mygdx.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by lingu on 2016/9/2.
 */
public class CombinationAction {
    private Music music;
    private Sound sound;
    private AnimatedImage animatedImage;
    private Image image;
    private Stage stage;
    public CombinationAction(Stage stage,Music music,AnimatedImage animatedImage){
        this.stage=stage;
        this.music=music;
        this.animatedImage=animatedImage;
    }
    public void playMusic(){
        if (music != null) {
            music.play();
        }
    }
    public void playSound(){
        if (sound != null) {
            sound.play();
        }
    }
    public void playAnimatedImage(){
        if (animatedImage != null) {
            stage.addActor(animatedImage);
        }
    }
    public void showImage(){
        if (image != null) {
            stage.addActor(image);
        }
    }
}
