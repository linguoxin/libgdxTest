package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

import java.util.Random;


/**
 * Created by lingu on 2016/8/31.
 */
public class GuideScreen extends ScreenAdapter {
    Image orangeImage;
    Image greenImage;
    Image blackImage;
    Image moveBlackImage;
    Image redImage;
    Image yellowImage;
    Image blueImage;
    Image purpleImage;
    WonderfulColor game;
    float time;
    Music angleSpeak03;
    private Button btnSanjianse;
    private Button btnRGB;
    ;
    private Image imgRGB;
    private SpriteBatch mSpriteBatch;
    AnimatedImage fingerAnimatedImage;
    AnimatedImage circle3;
    boolean isDrawStar;
    Music fingerMusic;
    private OrthographicCamera mCamera;
    Image[] images;
    Texture starBackground;
    Texture circle;

    Stage colorMixStage;

    public GuideScreen(WonderfulColor game) {
        this.game = game;
        mCamera = new OrthographicCamera(1280, 800);
        mCamera.position.set(1280 / 2, 800 / 2, 0);
        mSpriteBatch = new SpriteBatch();
        initWelcomeStage();
        colorMixStage = new Stage(new ScalingViewport(Scaling.stretch, 1280, 800, new OrthographicCamera()));
    }

    @Override
    public void render(float delta) {
        draw(Assets.welcomeStage, delta);

    }

    public void draw(Stage stage, float delta) {
        time += delta;
        GL20 gl = Gdx.gl;
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        stage.act(delta);


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
        redImage.addAction(Actions.sequence(Actions.delay(4f), Actions.scaleTo(2f, 2f, 0.2f)
                , Actions.scaleTo(1f, 1f, 0.2f), Actions.scaleTo(1.5f, 1.5f, 0.2f)
                , Actions.scaleTo(1f, 1f, 0.2f)));
        Assets.welcomeStage.addActor(redImage);
        ImageResource yellow = new ImageResource(Assets.textAtlas, "yellow_big", 400, 70);
        Image yellowImage = yellow.createImage();
        yellowImage.addAction(Actions.sequence(Actions.delay(5f), Actions.scaleTo(2f, 2f, 0.2f)
                , Actions.scaleTo(1f, 1f, 0.2f), Actions.scaleTo(1.5f, 1.5f, 0.2f)
                , Actions.scaleTo(1f, 1f, 0.2f)));
        Assets.welcomeStage.addActor(yellowImage);
        ImageResource blue = new ImageResource(Assets.textAtlas, "blue_big", 600, 100);
        Image blueImage = blue.createImage();
        blueImage.addAction(Actions.sequence(Actions.delay(6f), Actions.scaleTo(2f, 2f, 0.2f)
                , Actions.scaleTo(1f, 1f, 0.2f), Actions.scaleTo(1.5f, 1.5f, 0.2f)
                , Actions.scaleTo(1f, 1f, 0.2f)));
        Assets.welcomeStage.addActor(blueImage);
        angleSpeak03 = Gdx.audio.newMusic(Gdx.files.internal("res/angle03.mp3"));
        angleSpeak03.play();
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                final Music right = Gdx.audio.newMusic(Gdx.files.internal("res/answer_right.mp3"));
                ImageResource btnSanyuanseResource = new ImageResource(Assets.textAtlas, "btn_sanyuan_normal"
                        , "btn_sanyuan_select", 0, 400, new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (fingerMusic != null && !fingerMusic.isPlaying()) {
                            right.play();
                        }
                        explainColor();
                    }
                });
                final Music notRight = Gdx.audio.newMusic(Gdx.files.internal("res/not_right.mp3"));
                btnRGB = btnSanyuanseResource.createButton();
                Assets.welcomeStage.addActor(btnRGB);
                ImageResource btnSanjianseResource = new ImageResource(Assets.textAtlas, "btn_sanjian_normal"
                        , "btn_sanjian_select", 475, 400, new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (fingerMusic != null && !fingerMusic.isPlaying())
                            notRight.play();
                        explainColor();

                    }
                });
                btnSanjianse = btnSanjianseResource.createButton();
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

    private void explainColor() {
        btnSanjianse.addAction(Actions.fadeOut(0.3f));
        Assets.welcomeStage.addActor(btnSanjianse);
        btnSanjianse.remove();
        btnRGB.addAction(Actions.moveTo(300, 400, 0.5f));
        Assets.welcomeStage.addActor(btnRGB);
        fingerAnimatedImage.remove();

        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                ImageResource imageResource = new ImageResource(Assets.textAtlas
                        , "btn_sanyuan_normal", 300, 400);
                imgRGB = imageResource.createImage();
                Assets.welcomeStage.addActor(imgRGB);
                btnRGB.remove();

            }
        }, 0.5f);
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                Music music = Gdx.audio.newMusic(Gdx.files.internal("res/angle04.mp3"));
                music.play();
                music.setOnCompletionListener(new Music.OnCompletionListener() {
                    @Override
                    public void onCompletion(Music music) {

                        createStar();
                        isDrawStar = true;
                    }
                });
            }
        }, 3f);


    }

    private void createStar() {
        Music angle05 = Gdx.audio.newMusic(Gdx.files.internal("res/angle05.mp3"));
        angle05.play();
        angle05.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                fingerAnimatedImage.setPosition(100, 450);
                Assets.welcomeStage.addActor(fingerAnimatedImage);
                fingerMusic.play();
                fingerMusic.setOnCompletionListener(new Music.OnCompletionListener() {
                    @Override
                    public void onCompletion(Music music) {
                        orangeImage.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                super.clicked(event, x, y);
                                Music answer_right1 = Gdx.audio.newMusic(Gdx.files.internal("res/answer_right1.mp3"));
                                answer_right1.play();
                                answer_right1.setOnCompletionListener(new Music.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(Music music) {
                                        showSecondScene();
                                    }
                                });
                                moveColor();
                            }
                        });
                        yellowImage.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                super.clicked(event, x, y);
                                Music not_right1 = Gdx.audio.newMusic(Gdx.files.internal("res/not_right1.mp3"));
                                not_right1.play();
                                moveColor();
                                not_right1.setOnCompletionListener(new Music.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(Music music) {
                                        showSecondScene();

                                    }
                                });

                            }
                        });
                        redImage.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                super.clicked(event, x, y);
                                Music not_right1 = Gdx.audio.newMusic(Gdx.files.internal("res/not_right1.mp3"));
                                not_right1.play();
                                moveColor();
                                not_right1.setOnCompletionListener(new Music.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(Music music) {
                                        showSecondScene();

                                    }
                                });
                            }
                        });

                    }
                });

            }
        });
        Pixmap pixmap = new Pixmap(1280, 800, Pixmap.Format.RGBA8888);
        pixmap.setColor(0xa5e9ffff);
        pixmap.fillRectangle(0, 0, 1280, 800);
        starBackground = new Texture(1280, 800, Pixmap.Format.RGBA8888);
        starBackground.draw(pixmap, 0, 0);
        Image starBackgroundImage = new Image(starBackground);
        Assets.welcomeStage.addActor(starBackgroundImage);
        TextureRegion textureRegion = Assets.textAtlas.findRegion("star");

        Random random = new Random();
        int num;
        images = new Image[88];
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 11; i++) {
                num = random.nextInt(5) + 1;
                Image image = new Image(textureRegion);
                image.setBounds(120 * i, 50 * (2 * j + 1), num * 16, num * 16);
                image.setOrigin(image.getWidth() / 2, image.getHeight() / 2);
                //  image.addAction(Actions.forever(Actions.rotateTo(-time%360*50)));
                Assets.welcomeStage.addActor(image);
            }
        }
        Assets.welcomeStage.addActor(Assets.closeButton);
        Assets.animatedFlipImage.setPosition(960, 150);
        Assets.welcomeStage.addActor(Assets.animatedFlipImage);
        AnimationResource redBoyResource = new AnimationResource("res/red_boy.atlas", 14, 0.15f, true);

        final AnimatedImage redBoyAnimatedImage = redBoyResource.createFilpAnimatedImage();

        redBoyAnimatedImage.setPosition(50, 250);
        redBoyAnimatedImage.addAction(Actions.moveTo(450, 250, 4f));
        Assets.welcomeStage.addActor(redBoyAnimatedImage);
        AnimationResource yellowBoyResource = new AnimationResource("res/yellow_boy.atlas", 14, 0.15f, true);

        final AnimatedImage yellowBoyAnimatedImage = yellowBoyResource.createAnimatedImage();

        yellowBoyAnimatedImage.setPosition(1200, 250);
        yellowBoyAnimatedImage.addAction(Actions.moveTo(550, 250, 4f));
        Assets.welcomeStage.addActor(yellowBoyAnimatedImage);
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                redBoyAnimatedImage.setStop(true);
                yellowBoyAnimatedImage.setStop(true);
                AnimationResource animationResource = new AnimationResource("res/circle1.atlas", 2,
                        0.5f, true);
                final AnimatedImage animatedImage = animationResource.createAnimatedImage();
                animatedImage.setPosition(530, 360);
                Assets.welcomeStage.addActor(animatedImage);
                Timer timer1 = new Timer();
                timer1.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        animatedImage.setStop(true);

                    }
                }, 5f);
                ImageResource orangeCircle = new ImageResource(Assets.textAtlas, "orangeCircle", 50, 500);
                orangeImage = orangeCircle.createImage();
                ImageResource redCircle = new ImageResource(Assets.textAtlas, "redCircle", 50, 300);
                redImage = redCircle.createImage();
                ImageResource yellowCircle = new ImageResource(Assets.textAtlas, "yellowCircle", 50, 100);
                yellowImage = yellowCircle.createImage();
                Assets.welcomeStage.addActor(orangeImage);
                Assets.welcomeStage.addActor(redImage);
                Assets.welcomeStage.addActor(yellowImage);

            }
        }, 2f);
    }

    private void moveColor() {
        yellowImage.remove();
        orangeImage.remove();
        redImage.remove();
        fingerAnimatedImage.remove();
        ImageResource imageResource = new ImageResource(Assets.textAtlas, "move_orange", 50, 500);
        Image image = imageResource.createImage();
        image.addAction(Actions.moveTo(530, 360, 2.5f));
        Assets.welcomeStage.addActor(image);
    }

    private void showSecondScene() {
        Music angle06 = Gdx.audio.newMusic(Gdx.files.internal("res/angle06.mp3"));
        angle06.play();
        ImageResource imageResource = new ImageResource(Assets.textAtlas, "second_background", 0, 0);
        Image background = imageResource.createImage();
        background.setSize(1280, 800);
        Assets.welcomeStage.addActor(background);
        Assets.welcomeStage.addActor(Assets.closeButton);
        Assets.welcomeStage.addActor(Assets.animatedFlipImage);
        angle06.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                purpleImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        Music answer_right1 = Gdx.audio.newMusic(Gdx.files.internal("res/answer_right2.mp3"));
                        answer_right1.play();
                        answer_right1.setOnCompletionListener(new Music.OnCompletionListener() {
                            @Override
                            public void onCompletion(Music music) {
                                showThirdScene();
                            }
                        });
                        moveColor2();
                    }
                });
                blueImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        Music not_right1 = Gdx.audio.newMusic(Gdx.files.internal("res/not_right2.mp3"));
                        not_right1.play();
                        moveColor2();
                        not_right1.setOnCompletionListener(new Music.OnCompletionListener() {
                            @Override
                            public void onCompletion(Music music) {
                                showThirdScene();

                            }
                        });

                    }
                });
                redImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        Music not_right1 = Gdx.audio.newMusic(Gdx.files.internal("res/not_right2.mp3"));
                        not_right1.play();
                        moveColor2();
                        not_right1.setOnCompletionListener(new Music.OnCompletionListener() {
                            @Override
                            public void onCompletion(Music music) {
                                showThirdScene();
                            }
                        });
                    }
                });
            }
        });
        AnimationResource blueBoyResource = new AnimationResource("res/blue_boy.atlas", 14, 0.15f, true);

        final AnimatedImage blueBoyAnimatedImage = blueBoyResource.createAnimatedImage();

        blueBoyAnimatedImage.setPosition(1200, 250);
        blueBoyAnimatedImage.addAction(Actions.moveTo(550, 250, 4f));
        Assets.welcomeStage.addActor(blueBoyAnimatedImage);
        AnimationResource redBoyResource = new AnimationResource("res/red_boy.atlas", 14, 0.15f, true);

        final AnimatedImage redBoyAnimatedImage = redBoyResource.createFilpAnimatedImage();

        redBoyAnimatedImage.setPosition(50, 250);
        redBoyAnimatedImage.addAction(Actions.moveTo(450, 250, 4f));
        Assets.welcomeStage.addActor(redBoyAnimatedImage);

        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                redBoyAnimatedImage.setStop(true);
                blueBoyAnimatedImage.setStop(true);
                AnimationResource animationResource = new AnimationResource("res/circle2.atlas", 2,
                        0.5f, true);
                final AnimatedImage animatedImage = animationResource.createAnimatedImage();
                animatedImage.setPosition(530, 360);
                Assets.welcomeStage.addActor(animatedImage);
                Timer timer1 = new Timer();
                timer1.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        animatedImage.setStop(true);

                    }
                }, 5f);
                ImageResource purpleCircle = new ImageResource(Assets.textAtlas, "purpleCircle", 50, 500);
                purpleImage = purpleCircle.createImage();
                ImageResource redCircle = new ImageResource(Assets.textAtlas, "redCircle", 50, 300);
                redImage = redCircle.createImage();
                ImageResource blueCircle = new ImageResource(Assets.textAtlas, "blueCircle", 50, 100);
                blueImage = blueCircle.createImage();
                Assets.welcomeStage.addActor(purpleImage);
                Assets.welcomeStage.addActor(redImage);
                Assets.welcomeStage.addActor(blueImage);

            }
        }, 2f);
    }

    private void showThirdScene() {
        Music angle06 = Gdx.audio.newMusic(Gdx.files.internal("res/angle07.mp3"));
        angle06.play();
        Pixmap pixmap = new Pixmap(1280, 800, Pixmap.Format.RGBA8888);
        pixmap.setColor(0xa5e9ffff);
        pixmap.fillRectangle(0, 0, 1280, 800);
        starBackground = new Texture(1280, 800, Pixmap.Format.RGBA8888);
        starBackground.draw(pixmap, 0, 0);
        Image starBackgroundImage = new Image(starBackground);
        Assets.welcomeStage.addActor(starBackgroundImage);
        Assets.welcomeStage.addActor(Assets.closeButton);
        ImageResource cloud1 = new ImageResource(Assets.textAtlas, "cloud1", 200, 500);
        ImageResource cloud2 = new ImageResource(Assets.textAtlas, "cloud2", 500, 400);
        ImageResource cloud3 = new ImageResource(Assets.textAtlas, "cloud3", 800, 300);
        Assets.welcomeStage.addActor(cloud1.createImage());
        Assets.welcomeStage.addActor(cloud2.createImage());
        Assets.welcomeStage.addActor(cloud3.createImage());
        Assets.welcomeStage.addActor(Assets.animatedFlipImage);

        angle06.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                greenImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        Music answer_right1 = Gdx.audio.newMusic(Gdx.files.internal("res/answer_right3.mp3"));
                        answer_right1.play();
                        answer_right1.setOnCompletionListener(new Music.OnCompletionListener() {
                            @Override
                            public void onCompletion(Music music) {
                                showFourScene();
                            }
                        });
                        moveColor3();
                    }
                });
                blueImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        Music not_right1 = Gdx.audio.newMusic(Gdx.files.internal("res/not_right3.mp3"));
                        not_right1.play();
                        moveColor3();
                        not_right1.setOnCompletionListener(new Music.OnCompletionListener() {
                            @Override
                            public void onCompletion(Music music) {
                                showFourScene();

                            }
                        });

                    }
                });
                yellowImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        Music not_right1 = Gdx.audio.newMusic(Gdx.files.internal("res/not_right3.mp3"));
                        not_right1.play();
                        moveColor3();
                        not_right1.setOnCompletionListener(new Music.OnCompletionListener() {
                            @Override
                            public void onCompletion(Music music) {
                                showFourScene();

                            }
                        });
                    }
                });
            }
        });
        AnimationResource yellowBoyResource = new AnimationResource("res/yellow_boy.atlas", 14, 0.15f, true);
        final AnimatedImage yellowBoyAnimatedImage = yellowBoyResource.createFilpAnimatedImage();
        yellowBoyAnimatedImage.setPosition(50, 250);
        yellowBoyAnimatedImage.addAction(Actions.moveTo(450, 250, 4f));
        Assets.welcomeStage.addActor(yellowBoyAnimatedImage);
        AnimationResource blueBoyResource = new AnimationResource("res/blue_boy.atlas", 14, 0.15f, true);

        final AnimatedImage blueBoyAnimatedImage = blueBoyResource.createAnimatedImage();

        blueBoyAnimatedImage.setPosition(1200, 250);
        blueBoyAnimatedImage.addAction(Actions.moveTo(550, 250, 4f));
        Assets.welcomeStage.addActor(blueBoyAnimatedImage);


        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                yellowBoyAnimatedImage.setStop(true);
                blueBoyAnimatedImage.setStop(true);
                AnimationResource animationResource = new AnimationResource("res/circle2.atlas", 2,
                        0.5f, true);
                final AnimatedImage animatedImage = animationResource.createAnimatedImage();
                animatedImage.setPosition(530, 360);
                Assets.welcomeStage.addActor(animatedImage);
                Timer timer1 = new Timer();
                timer1.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        animatedImage.setStop(true);

                    }
                }, 5f);
                ImageResource greenCircle = new ImageResource(Assets.textAtlas, "greenCircle", 50, 500);
                greenImage = greenCircle.createImage();
                ImageResource yellowCircle = new ImageResource(Assets.textAtlas, "yellowCircle", 50, 300);
                yellowImage = yellowCircle.createImage();
                ImageResource blueCircle = new ImageResource(Assets.textAtlas, "blueCircle", 50, 100);
                blueImage = blueCircle.createImage();
                Assets.welcomeStage.addActor(greenImage);
                Assets.welcomeStage.addActor(yellowImage);
                Assets.welcomeStage.addActor(blueImage);

            }
        }, 2f);
    }

    private void moveColor2() {
        purpleImage.remove();
        blueImage.remove();
        redImage.remove();
        ImageResource imageResource = new ImageResource(Assets.textAtlas, "move_purple", 50, 500);
        Image image = imageResource.createImage();
        image.addAction(Actions.moveTo(530, 360, 2.5f));
        Assets.welcomeStage.addActor(image);
    }

    private void moveColor3() {
        greenImage.remove();
        blueImage.remove();
        yellowImage.remove();
        ImageResource imageResource = new ImageResource(Assets.textAtlas, "move_green", 50, 500);
        Image image = imageResource.createImage();
        image.addAction(Actions.moveTo(530, 360, 2.5f));
        Assets.welcomeStage.addActor(image);
    }

    private void showFourScene() {
        Music angle06 = Gdx.audio.newMusic(Gdx.files.internal("res/angle08.mp3"));
        angle06.play();
        Pixmap pixmap = new Pixmap(1280, 800, Pixmap.Format.RGBA8888);
        pixmap.setColor(0xffccccff);
        pixmap.fillRectangle(0, 0, 1280, 800);
        starBackground = new Texture(1280, 800, Pixmap.Format.RGBA8888);
        starBackground.draw(pixmap, 0, 0);
        Image starBackgroundImage = new Image(starBackground);
        Assets.welcomeStage.addActor(starBackgroundImage);
        TextureRegion textureRegion = Assets.textAtlas.findRegion("star");

        Random random = new Random();
        int num;
        images = new Image[88];
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 11; i++) {
                num = random.nextInt(5) + 1;
                Image image = new Image(textureRegion);
                image.setBounds(120 * i, 50 * (2 * j + 1), num * 16, num * 16);
                image.setOrigin(image.getWidth() / 2, image.getHeight() / 2);
                //  image.addAction(Actions.forever(Actions.rotateTo(-time%360*50)));
                Assets.welcomeStage.addActor(image);
            }
        }
        Assets.welcomeStage.addActor(Assets.closeButton);
        Assets.welcomeStage.addActor(Assets.animatedFlipImage);

        angle06.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                blackImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        Music answer_right1 = Gdx.audio.newMusic(Gdx.files.internal("res/answer_right4.mp3"));
                        answer_right1.play();
                        answer_right1.setOnCompletionListener(new Music.OnCompletionListener() {
                            @Override
                            public void onCompletion(Music music) {
                                showFinalScene();
                            }
                        });
                        moveColor4();
                    }
                });
                purpleImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        Music not_right1 = Gdx.audio.newMusic(Gdx.files.internal("res/not_right4.mp3"));
                        not_right1.play();
                        moveColor4();
                        not_right1.setOnCompletionListener(new Music.OnCompletionListener() {
                            @Override
                            public void onCompletion(Music music) {
                                showFinalScene();

                            }
                        });

                    }
                });
                greenImage.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        Music not_right1 = Gdx.audio.newMusic(Gdx.files.internal("res/not_right4.mp3"));
                        not_right1.play();
                        moveColor4();
                        not_right1.setOnCompletionListener(new Music.OnCompletionListener() {
                            @Override
                            public void onCompletion(Music music) {
                                showFinalScene();

                            }
                        });
                    }
                });
            }
        });
        AnimationResource blueBoyResource = new AnimationResource("res/blue_boy.atlas", 14, 0.15f, true);

        final AnimatedImage blueBoyAnimatedImage = blueBoyResource.createAnimatedImage();

        blueBoyAnimatedImage.setPosition(700, 400);
        blueBoyAnimatedImage.addAction(Actions.moveTo(520, 320, 4f));
        Assets.welcomeStage.addActor(blueBoyAnimatedImage);
        AnimationResource redBoyResource = new AnimationResource("res/red_boy.atlas", 14, 0.15f, true);
        final AnimatedImage redBoyAnimatedImage = redBoyResource.createFilpAnimatedImage();
        redBoyAnimatedImage.setPosition(50, 250);
        redBoyAnimatedImage.addAction(Actions.moveTo(450, 250, 4f));
        Assets.welcomeStage.addActor(redBoyAnimatedImage);
        AnimationResource yellowBoyResource = new AnimationResource("res/yellow_boy.atlas", 14, 0.15f, true);

        final AnimatedImage yellowBoyAnimatedImage = yellowBoyResource.createAnimatedImage();

        yellowBoyAnimatedImage.setPosition(1200, 250);
        yellowBoyAnimatedImage.addAction(Actions.moveTo(550, 250, 4f));
        Assets.welcomeStage.addActor(yellowBoyAnimatedImage);


        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                yellowBoyAnimatedImage.setStop(true);
                blueBoyAnimatedImage.setStop(true);
                redBoyAnimatedImage.setStop(true);

                AnimationResource animationResource = new AnimationResource("res/circle3.atlas", 2,
                        0.5f, true);
                circle3 = animationResource.createAnimatedImage();
                circle3.setPosition(530, 360);
                Assets.welcomeStage.addActor(circle3);
                Timer timer1 = new Timer();
                timer1.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        circle3.setStop(true);

                    }
                }, 5f);
                ImageResource blackCircle = new ImageResource(Assets.textAtlas, "blackCircle", 50, 500);
                blackImage = blackCircle.createImage();
                ImageResource purpleCircle = new ImageResource(Assets.textAtlas, "purpleCircle", 50, 300);
                purpleImage = purpleCircle.createImage();
                ImageResource greenCircle = new ImageResource(Assets.textAtlas, "greenCircle", 50, 100);
                greenImage = greenCircle.createImage();
                Assets.welcomeStage.addActor(blackImage);
                Assets.welcomeStage.addActor(purpleImage);
                Assets.welcomeStage.addActor(greenImage);

            }
        }, 2f);
    }

    private void moveColor4() {
        blackImage.remove();
        purpleImage.remove();
        greenImage.remove();
        ImageResource imageResource = new ImageResource(Assets.textAtlas, "move_black", 50, 500);
        moveBlackImage = imageResource.createImage();
        moveBlackImage.addAction(Actions.moveTo(530, 360, 2.5f));
        Assets.welcomeStage.addActor(moveBlackImage);
    }

    private void showFinalScene() {
        float firstDelay = 15f;
        float spaceTime = 5f;
        moveBlackImage.remove();
        circle3.remove();
        Music angle06 = Gdx.audio.newMusic(Gdx.files.internal("res/angle09.mp3"));
        angle06.play();
        AnimationResource animationResource1 = new AnimationResource("res/intersect1.atlas", 2,
                0.5f, true);
        final AnimatedImage animatedImage1 = animationResource1.createAnimatedImage();
        animatedImage1.addAction(Actions.sequence(Actions.fadeOut(0f), Actions.delay(firstDelay),
                Actions.fadeIn(0f), Actions.delay(firstDelay + spaceTime), Actions.fadeOut(0f)));
        animatedImage1.setPosition(530, 360);
        Assets.welcomeStage.addActor(animatedImage1);
        AnimationResource animationResource2 = new AnimationResource("res/intersect2.atlas", 2,
                0.5f, true);
        final AnimatedImage animatedImage2 = animationResource2.createAnimatedImage();
        animatedImage2.addAction(Actions.sequence(Actions.fadeOut(0f), Actions.delay(firstDelay + spaceTime), Actions.fadeIn(0f)
                , Actions.delay(firstDelay + 2 * spaceTime), Actions.fadeOut(0f)));
        animatedImage2.setPosition(530, 360);
        Assets.welcomeStage.addActor(animatedImage2);
        AnimationResource animationResource3 = new AnimationResource("res/intersect3.atlas", 2,
                0.5f, true);
        final AnimatedImage animatedImage3 = animationResource3.createAnimatedImage();
        animatedImage3.addAction(Actions.sequence(Actions.fadeOut(0f), Actions.delay(firstDelay + 2 * spaceTime), Actions.fadeIn(0f)
                , Actions.delay(firstDelay + 3 * spaceTime), Actions.fadeOut(0f)));
        animatedImage3.setPosition(530, 360);
        Assets.welcomeStage.addActor(animatedImage3);
        AnimationResource animationResource4 = new AnimationResource("res/intersect4.atlas", 2,
                0.5f, true);
        final AnimatedImage animatedImage4 = animationResource4.createAnimatedImage();
        animatedImage4.addAction(Actions.sequence(Actions.fadeOut(0f), Actions.delay(firstDelay + 3 * spaceTime), Actions.fadeIn(0f)
                , Actions.delay(firstDelay + 4 * spaceTime), Actions.fadeOut(0f)));
        animatedImage4.setPosition(540, 360);
        Assets.welcomeStage.addActor(animatedImage4);
    }
}
