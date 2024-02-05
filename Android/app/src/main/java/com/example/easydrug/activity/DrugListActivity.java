package com.example.easydrug.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easydrug.Configs;
import com.example.easydrug.R;
import com.example.easydrug.Utils.FileUtil;
import com.example.easydrug.Utils.FinishActivityEvent;
import com.example.easydrug.adapter.ContentAdapter;
import com.example.easydrug.adapter.DrugListAdapter;
import com.example.easydrug.adapter.TagAdapter;
import com.example.easydrug.model.DrugList;
import com.example.easydrug.netservice.Api.DrugService;
import com.githang.statusbar.StatusBarCompat;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class DrugListActivity extends FragmentActivity {

    private ImageView back;
    private TextView edit, refresh;
    private RecyclerView drugList;
    private DrugListAdapter drugListAdapter;
    private ConstraintLayout loadingView, errorView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_list);
        StatusBarCompat.setStatusBarColor(this, this.getResources().getColor(R.color.bg_color));
        EventBus.getDefault().register(this);
        back = findViewById(R.id.back);
        edit = findViewById(R.id.edit);
        drugList = findViewById(R.id.drug_list);
        loadingView = findViewById(R.id.loading_view);
        errorView = findViewById(R.id.loading_error_view);
        refresh = findViewById(R.id.refresh);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDrugList();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drugListAdapter != null) {
                    if (edit.getText().toString().equals("Edit")) {
                        drugListAdapter.setDeleteMode(true);
                        edit.setText("Done");
                    } else {
                        drugListAdapter.setDeleteMode(false);
                        edit.setText("Edit");
                    }
                }
            }
        });

        requestDrugList();
    }

    private void requestDrugList() {
        loadingView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);

        DrugService.getInstance().getDrugList(FileUtil.getSPString(this, Configs.userNameKey))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DrugList>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DrugList value) {
                        loadingView.setVisibility(View.GONE);

                        if (value.getCode() == Configs.requestSuccess) {

                            drugListAdapter = new DrugListAdapter(DrugListActivity.this, value.getDrugList());
                            drugList.setAdapter(drugListAdapter);
                        } else {
                            errorView.setVisibility(View.VISIBLE);
                            Toast.makeText(DrugListActivity.this, value.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingView.setVisibility(View.GONE);
                        errorView.setVisibility(View.VISIBLE);
                        Toast.makeText(DrugListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FinishActivityEvent event) {
        if (event.scene == FinishActivityEvent.DRUGLIST) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
