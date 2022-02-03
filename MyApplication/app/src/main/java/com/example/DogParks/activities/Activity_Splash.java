package com.example.DogParks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.annotations.Nullable;
import com.example.DogParks.R;

public class Activity_Splash extends AppCompatActivity {

    private ImageView splash_logo;
    private TextView splash_title;
    private static final int splashTimeOut = 5000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        findViews();
        animation();
    }

    private void animation() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Activity_Splash.this, Activity_Login.class);
                startActivity(i);
                finish();
            }
        }, splashTimeOut);

        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mysplashanimation);
        splash_logo.startAnimation(myanim);
        splash_title.startAnimation(myanim);
    }

        private void findViews(){
            splash_title =findViewById(R.id.splash_title);
            splash_logo = findViewById(R.id.splash_logo);

        }
}