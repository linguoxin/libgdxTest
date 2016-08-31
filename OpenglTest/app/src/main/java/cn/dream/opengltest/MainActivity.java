package cn.dream.opengltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.dream.opengltest.tools.TextureAtlas;

public class MainActivity extends AppCompatActivity {
    Glview glview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        glview = (Glview) findViewById(R.id.glview);
        TextureAtlas textureAtlas = new TextureAtlas(this, "big.atlas");
        textureAtlas.parseFile();

    }
}
