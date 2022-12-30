package com.ibelm.quicklteforce4g;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.BuildCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_SCREEN = 2300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //DARK MODE
        SharedPreferences preferences_ = getSharedPreferences("dark_mode", MODE_PRIVATE);
        String state = preferences_.getString("state", "");

        if (state.equals("true")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else if (state.equals("false")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            if (BuildCompat.isAtLeastP()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
            }

        }

        SharedPreferences preferences = getSharedPreferences("intro",MODE_PRIVATE);
        String intro = preferences.getString("state","");

        ImageView schedule_time_icon = findViewById(R.id.app_logo);
        schedule_time_icon.setAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_in));

        SharedPreferences prefs = getSharedPreferences("PHONE", MODE_PRIVATE);
        String info = prefs.getString("INFO", "");
        if (info.equals("true")){
            try {
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.setClassName("com.android.phone", "com.android.phone.settings.RadioInfo");
                //intent.setClassName("com.android.settings", "com.android.settings.RadioInfo");
                finish();
                startActivity(intent);
            } catch (Exception e10) {
                e10.printStackTrace();
            }
            SharedPreferences.Editor editor = getSharedPreferences("PHONE", MODE_PRIVATE).edit();
            editor.putString("INFO", "false");
            editor.apply();
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (intro.equals("done")){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        finish();
                    }else {
                        //if first time
                        Intent intent = new Intent(getApplicationContext(), IntroScreenActivity.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        finish();
                    }
                    //startActivity(new Intent(SplashScreen.this, MainActivity.class));

                }
            }, SPLASH_SCREEN);
        }
    }
}