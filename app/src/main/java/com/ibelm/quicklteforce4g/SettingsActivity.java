package com.ibelm.quicklteforce4g;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.os.BuildCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;

public class SettingsActivity extends AppCompatActivity {

    private LinearLayout layoutContact,layoutContact_;
    private boolean contactBool = false;
    private SwitchCompat switchCompat;

    ReviewInfo reviewInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().hide();/*
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        android.graphics.drawable.Drawable background = getResources().getDrawable(R.drawable.status);
        getWindow().setBackgroundDrawable(background);*/

        // Color gradiant in textView
        /*TextView textView = (TextView) findViewById(R.id.textView1);
        textView.setTextColor(Color.parseColor("#FFA52172"));

        TextPaint paint = textView.getPaint();
        float width = paint.measureText("Settings");

        Shader textShader = new LinearGradient(0, 0, width, textView.getTextSize(),
                new int[]{
                        Color.parseColor("#FFA52172"),
                        Color.parseColor("#FF363A91"),
                        Color.parseColor("#FF0179C0")
                }, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(textShader);*/

        switchCompat = findViewById(R.id.settings_switchCompat);

        //DARK MODE
        SharedPreferences preferences = getSharedPreferences("dark_mode", MODE_PRIVATE);
        String state = preferences.getString("state", "");

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

        if (state.equals("true")) {
            switchCompat.setChecked(true);
        } else if (state.equals("false")) {
            switchCompat.setChecked(false);
        }

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    switchCompat.setChecked(true);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("state", "true");
                    editor.apply();
                } else {

                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    switchCompat.setChecked(false);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("state", "false");
                    editor.apply();
                }
                refresh();

                //getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                //recreate();

                //Intent intent = new Intent();
                //intent.setAction("action_theme_changed");
            }
        });

        findViewById(R.id.layoutPrivacyPolicy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.termsfeed.com/live/a0c4a52a-b02d-4a98-a5d5-7fb4cfd65c3f")));
            }
        });

        findViewById(R.id.layoutRate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Review in google play
                ReviewManager manager = ReviewManagerFactory.create(SettingsActivity.this);
                Task<ReviewInfo> request = manager.requestReviewFlow();
                request.addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // We can get the ReviewInfo object
                        reviewInfo = task.getResult();
                    } else {
                        Toast.makeText(SettingsActivity.this, getResources().getString(R.string.review_failed_to_start), Toast.LENGTH_SHORT).show();
                    }
                });
                if (reviewInfo != null) {
                    Task<Void> flow = manager.launchReviewFlow((Activity) SettingsActivity.this, reviewInfo);
                    flow.addOnCompleteListener(task -> {
                        // The flow has finished. The API does not indicate whether the user
                        // reviewed or not, or even whether the review dialog was shown. Thus, no
                        // matter the result, we continue our app flow.
                    });
                }
            }
        });

        layoutContact = findViewById(R.id.layoutContact);
        layoutContact_ = findViewById(R.id.layoutContact_);
        layoutContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!contactBool){
                    layoutContact_.setVisibility(View.VISIBLE);
                }else {
                    layoutContact_.setVisibility(View.GONE);
                }
                contactBool = !contactBool;
            }
        });
        socialMedia();

        findViewById(R.id.layoutShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
                i.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + getPackageName());
                startActivity(Intent.createChooser(i, "Share URL"));
            }
        });

        findViewById(R.id.layoutMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Belmokhtar+Idir")));
            }
        });

    }

    private void refresh() {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void socialMedia() {
        LinearLayout instagram_home, facebook_home, whatsapp_home, messenger_home, gmail_home;

        instagram_home = findViewById(R.id.layoutContact_Instagram);
        facebook_home = findViewById(R.id.layoutContact_Facebook);
        whatsapp_home = findViewById(R.id.layoutContact_WhatsApp);
        messenger_home = findViewById(R.id.layoutContact_Messenger);
        gmail_home = findViewById(R.id.layoutContact_Gmail);

        instagram_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.lien_instagram))));
            }
        });
        facebook_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.lien_facebook))));
            }
        });
        whatsapp_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //send msg
                    Uri uri = Uri.parse("smsto:" + getString(R.string.lien_whatsapp));
                    Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                    i.setPackage("com.whatsapp");
                    startActivity(i);
                } catch (Exception e) {
                    Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp"));
                    startActivity(i);//https://whatsapp.com/dl/
                    e.printStackTrace();
                }
            }
        });
        messenger_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://messaging/" + getString(R.string.FbUserID)));
                    startActivity(i);

                } catch (Exception e) {
                    Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.facebook.orca"));
                    startActivity(i);//https://whatsapp.com/dl/
                    e.printStackTrace();
                }
            }
        });
        gmail_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("vnd.android.cursor.dir/email");
                String to[] = {getResources().getString(R.string.gmail)};
                emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
                startActivity(Intent.createChooser(emailIntent, "Send email..."));

            }
        });
    }
}