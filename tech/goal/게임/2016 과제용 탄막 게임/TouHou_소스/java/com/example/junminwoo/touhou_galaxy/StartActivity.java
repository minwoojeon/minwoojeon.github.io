package com.example.junminwoo.touhou_galaxy;

import android.app.Activity;
import android.os.Bundle;

import framework.GameView;
import framework.src.SoundManager.Game_sound;

public class StartActivity extends Activity {
    public void onCreate(Bundle savedInstence){
        super.onCreate(savedInstence);
        Game_sound.getS_Instance().Init(this);
        Game_sound.getS_Instance().AddSound(1, R.raw.intro);
        Game_sound.getS_Instance().AddSound(2, R.raw.arrow);
        Game_sound.getS_Instance().AddSound(3, R.raw.ball);
        long time = System.currentTimeMillis();
        setContentView(new GameView(this));
    }
}
