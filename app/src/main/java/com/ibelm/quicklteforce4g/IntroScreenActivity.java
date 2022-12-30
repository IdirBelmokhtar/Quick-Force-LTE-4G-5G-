package com.ibelm.quicklteforce4g;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


public class IntroScreenActivity extends AppCompatActivity {

    private ViewPager slide_intro_ViewPager;
    private LinearLayout dots_intro_Layout;
    private TextView skip_intro;

    private int mCurrentPage;

    private TextView[] mDots;

    private SliderIntroAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_screen);

        getSupportActionBar().hide();
        //getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.green_write_2));

        this.slide_intro_ViewPager = (ViewPager) findViewById(R.id.slide_intro_ViewPager);
        this.dots_intro_Layout = (LinearLayout) findViewById(R.id.dots_intro_Layout);
        this.skip_intro = (TextView) findViewById(R.id.next_intro);

        adapter = new SliderIntroAdapter(this);

        slide_intro_ViewPager.setAdapter(adapter);

        addDotsIndicator(0);

        slide_intro_ViewPager.addOnPageChangeListener(listener);

        skip_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("intro", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("state", "done");
                editor.apply();

                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    public void addDotsIndicator(int position) {

        mDots = new TextView[2];
        dots_intro_Layout.removeAllViews();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(4, 0, 4, 0);

        for (int i = 0; i < mDots.length; i++) {
            //The Unicode and HTML Entities of Bullet Point is (&#8226;) you find it here https://unicode-table.com/fr/html-entities/
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8728;"));
            mDots[i].setTextSize(32);
            //DARK MODE
            SharedPreferences preferences_ = getSharedPreferences("dark_mode", MODE_PRIVATE);
            String state = preferences_.getString("state", "");
            if (state.equals("true")) {
                mDots[i].setTextColor(getResources().getColor(R.color.white));
            } else if (state.equals("false")) {
                mDots[i].setTextColor(getResources().getColor(R.color.gray));
            } else {
                mDots[i].setTextColor(getResources().getColor(R.color.gray));
            }
            mDots[i].setLayoutParams(params);

            dots_intro_Layout.addView(mDots[i]);
        }

        if (mDots.length > 0) {
            mDots[position].setText(Html.fromHtml("&#8226;"));
            mDots[position].setTextSize(44);
            //DARK MODE
            SharedPreferences preferences_ = getSharedPreferences("dark_mode", MODE_PRIVATE);
            String state = preferences_.getString("state", "");
            if (state.equals("true")) {
                mDots[position].setTextColor(getResources().getColor(R.color.white));
            } else if (state.equals("false")) {
                mDots[position].setTextColor(getResources().getColor(R.color.gray));
            } else {
                mDots[position].setTextColor(getResources().getColor(R.color.gray));
            }
            mDots[position].setLayoutParams(params);
        }

    }

    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            mCurrentPage = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}