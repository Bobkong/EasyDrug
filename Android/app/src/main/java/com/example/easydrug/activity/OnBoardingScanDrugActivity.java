package com.example.easydrug.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.easydrug.Configs;
import com.example.easydrug.R;
import com.example.easydrug.Utils.RouteUtil;
import com.githang.statusbar.StatusBarCompat;

public class OnBoardingScanDrugActivity extends Activity{
    private ConstraintLayout letsGoScanDrug;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboardingscandrug);
        StatusBarCompat.setStatusBarColor(this, this.getResources().getColor(R.color.bg_color));
        letsGoScanDrug = findViewById(R.id.letsgo_scandrug);
        letsGoScanDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnBoardingScanDrugActivity.this, ScanDrugActivity.class);
                intent.putExtra(Configs.drugDetailFromScene, RouteUtil.fromOnBoarding);
                startActivity(intent);
            }
        });
    }
}


