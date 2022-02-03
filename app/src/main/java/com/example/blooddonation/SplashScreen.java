package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blooddonation.Auth.LoginActivity;

public class SplashScreen extends AppCompatActivity {

    ImageView ivBlood;
    TextView tvText;

    Animation imageAnimation, textAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        ivBlood = findViewById(R.id.ivBlood);
        tvText = findViewById(R.id.tvText);

        imageAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.image_animation);
        textAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.text_animation);

        ivBlood.setAnimation(imageAnimation);
        tvText.setAnimation(textAnimation);

        int SPLASH_SCREEN = 3500;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                finish();
            }
        }, SPLASH_SCREEN);

    }
}