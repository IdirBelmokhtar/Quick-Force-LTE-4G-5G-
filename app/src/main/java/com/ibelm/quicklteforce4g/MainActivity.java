package com.ibelm.quicklteforce4g;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.BuildCompat;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextPaint;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();/*
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        android.graphics.drawable.Drawable background = getResources().getDrawable(R.drawable.status);
        getWindow().setBackgroundDrawable(background);*/

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

        SharedPreferences prefs = getSharedPreferences("DIALOG", MODE_PRIVATE);
        String help = prefs.getString("help", "");

        if (!help.equals("true")){
            SharedPreferences.Editor editor = getSharedPreferences("DIALOG", MODE_PRIVATE).edit();
            editor.putString("help", "true");
            editor.apply();
            assistance(MainActivity.this);
        }

        //color gradiant TextView
        /*TextView textView = (TextView) findViewById(R.id.textView);
        textView.setTextColor(Color.parseColor("#FFA52172"));

        TextPaint paint = textView.getPaint();
        float width = paint.measureText("4G LTE Mode");

        Shader textShader = new LinearGradient(0, 0, width, textView.getTextSize(),
                new int[]{
                        Color.parseColor("#FFA52172"),
                        Color.parseColor("#FF363A91"),
                        Color.parseColor("#FF0179C0")
                }, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(textShader);*/

        findViewById(R.id.lte_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 30) {
                    T();
                } else {
                    V();
                }
            }
        });

        findViewById(R.id.internet_speed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SpeedTestActivity.class));
            }
        });

        findViewById(R.id.sim_information).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                I();
            }
        });

        findViewById(R.id.wifi_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Y();
            }
        });

        findViewById(R.id.more_informaion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                U();
            }
        });

        findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });

        findViewById(R.id.assistance).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assistance(MainActivity.this);
            }
        });

    }

    public final void assistance(Context context) {
        DialogAssistance dialogAssistance = new DialogAssistance((Activity) context);
        dialogAssistance.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogAssistance.setCancelable(true);
        dialogAssistance.show();
        //dialogAssistance.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    public final void T() {
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setClassName("com.android.phone", "com.android.phone.settings.RadioInfo");
            //intent.setClassName("com.android.phone", "com.android.phone.settings");
            startActivity(intent);
        } catch (Exception e10) {
            e10.printStackTrace();
        }
    }

    public final void I() {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS);
            startActivity(intent);
        } catch (Exception e10) {
            e10.printStackTrace();
            H();
        }
    }

    public final void H() {
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setClassName("com.android.phone", "com.android.phone.MobileNetworkSettings");
            //intent.setClassName("com.android.phone", "com.android.phone.settings");
            startActivity(intent);
        } catch (Exception e10) {
            e10.printStackTrace();
            G();
        }
    }

    public final void G() {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setComponent(new ComponentName("com.android.settings",
                    "com.android.settings.Settings$DataUsageSummaryActivity"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Android 11- (Testing Info)
    public final void U() {
        try {
            Runtime.getRuntime().exec("am start --user 0 -n com.android.settings/.Settings$TestingSettingsActivity");
        } catch (IOException e10) {
            e10.printStackTrace();
            Toast.makeText(this, e10.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Android 11-
    public final void V() {
        try {
            Runtime.getRuntime().exec("am start --user 0 -n com.android.settings/.RadioInfo");
        } catch (IOException e10) {
            e10.printStackTrace();
        }
    }

    public final void W() {
        try {
            Runtime.getRuntime().exec("adb shell am start -a android.settings");
            //Runtime.getRuntime().exec("am start --user 0 -n com.android.phone/.settings.RadioInfo");
            Toast.makeText(this, "d", Toast.LENGTH_SHORT).show();

        } catch (IOException e10) {
            Toast.makeText(this, "qsdfg", Toast.LENGTH_SHORT).show();
            e10.printStackTrace();
        }
    }

    public final void X() {
        try {
            Runtime.getRuntime().exec("am start --user 0 -n com.android.settings/.RadioInfo");
        } catch (IOException e10) {
            e10.printStackTrace();
        }
    }

    public final void Y() {
        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    }

}