package com.example.easydrug.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.easydrug.Configs;
import com.example.easydrug.R;
import com.example.easydrug.Utils.FileUtil;
import com.githang.statusbar.StatusBarCompat;

public class SplashActivity extends AppCompatActivity {

    private static String TAG = "SplashActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, this.getResources().getColor(R.color.transparent_theme_color));

        new Handler().postDelayed(() -> {
            boolean ifSignedUp = FileUtil.getSPBool(SplashActivity.this, Configs.ifSignedUpKey);
            // determine go to MainActivity or OnboardingActivity
            if (!ifSignedUp) {
                startActivity(new Intent(SplashActivity.this, OnboardingActivity.class));
            } else if (FileUtil.getSPString(SplashActivity.this, Configs.userNameKey) == null){
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }else {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }

            finish();
        }, 300);
    }
}
