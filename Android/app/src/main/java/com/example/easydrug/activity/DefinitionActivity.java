package com.example.easydrug.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easydrug.R;
import com.example.easydrug.Utils.SpeechUtil;
import com.example.easydrug.adapter.DefinitionAdapter;
import com.example.easydrug.model.SideEffectPossibility;
import com.githang.statusbar.StatusBarCompat;

import java.util.ArrayList;

public class DefinitionActivity extends Activity {

    private RecyclerView definitionList;
    private ArrayList<SideEffectPossibility> mData;
    private DefinitionAdapter mAdapter;
    private ImageView back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);
        StatusBarCompat.setStatusBarColor(this, this.getResources().getColor(R.color.bg_color));

        definitionList = findViewById(R.id.definition_list);

        mData = (ArrayList<SideEffectPossibility>) getIntent().getSerializableExtra("possibilities");

        if (mData != null && !mData.isEmpty()) {
            definitionList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            mAdapter = new DefinitionAdapter(mData, this);
            definitionList.setAdapter(mAdapter);
        }

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpeechUtil.destroy();
    }

}
