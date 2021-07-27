package com.example.wolfbooksapp.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.example.wolfbooksapp.R;
import com.example.wolfbooksapp.helper.Constant;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ivLogo = findViewById(R.id.iv_logo);

        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.translate);
        animator.setTarget(ivLogo);
        animator.start();

        //
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, NavigationActivity.class);
                startActivity(intent);
                finish();
            }
        }, Constant.SPLASH_TIME_OUT);
    }
}
