package com.example.easydrug.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easydrug.Configs;
import com.example.easydrug.R;
import com.example.easydrug.adapter.ContentAdapter;
import com.example.easydrug.adapter.TagAdapter;
import com.example.easydrug.model.ResourcesResponse;
import com.example.easydrug.netservice.Api.ResourceService;
import com.githang.statusbar.StatusBarCompat;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class ExploreActivity extends Activity {
    private RecyclerView tagView;
    private RecyclerView contentView;
    private TagAdapter tagAdapter;
    private ContentAdapter contentAdapter;
    private ConstraintLayout loadingView, errorView;
    private ImageView back;
    private TextView refresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        StatusBarCompat.setStatusBarColor(this, this.getResources().getColor(R.color.bg_color));

        tagView = findViewById(R.id.tag_list);
        contentView = findViewById(R.id.content_list);
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> finish());

        loadingView = findViewById(R.id.loading_view);
        errorView = findViewById(R.id.loading_error_view);
        refresh = findViewById(R.id.refresh);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestExplore();
            }
        });

        requestExplore();

    }
    private void requestExplore() {
        loadingView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);

        ResourceService.getInstance().getResources()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResourcesResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResourcesResponse value) {
                        loadingView.setVisibility(View.GONE);

                        if (value.getCode() == Configs.requestSuccess) {

                            tagAdapter = new TagAdapter(value.getTag_list(), ExploreActivity.this);
                            tagView.setAdapter(tagAdapter);

                            contentAdapter = new ContentAdapter(value.getResource_list(), ExploreActivity.this);
                            contentView.setAdapter(contentAdapter);
                        } else {
                            errorView.setVisibility(View.VISIBLE);
                            Toast.makeText(ExploreActivity.this, value.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorView.setVisibility(View.VISIBLE);
                        loadingView.setVisibility(View.GONE);
                        Toast.makeText(ExploreActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void selectTag(String tagName) {
        contentAdapter.setTag(tagName);
    }

    public void resetTag() {
        contentAdapter.resetTag();
    }
}
