package com.example.easydrug.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
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
import com.example.easydrug.Utils.FileUtil;
import com.example.easydrug.Utils.FinishActivityEvent;
import com.example.easydrug.Utils.SpeechUtil;
import com.example.easydrug.adapter.FoodInteractionAdapter;
import com.example.easydrug.model.FoodInteraction;
import com.example.easydrug.model.FoodInteractionDetail;
import com.example.easydrug.netservice.Api.DrugService;
import com.githang.statusbar.StatusBarCompat;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import retrofit2.http.Body;

public class FoodInteractionActivity extends Activity {

    private String TAG = "FoodInteractionActivity";
    private ConstraintLayout noInteractionView, checkAgain, errorView, loadingView;
    private TextView done;
    private ImageView back;
    private RecyclerView interactionList;
    private TextView refresh, disclaimer;
    private FoodInteractionAdapter adapter;
    private ArrayList<String> ingredients;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_interaction);
        StatusBarCompat.setStatusBarColor(this, this.getResources().getColor(R.color.bg_color));

        noInteractionView = findViewById(R.id.no_interaction_view);
        checkAgain = findViewById(R.id.check_again);
        loadingView = findViewById(R.id.loading_view);
        errorView = findViewById(R.id.loading_error_view);
        disclaimer = findViewById(R.id.disclaimer);
        refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestFoodInteractions(ingredients);
            }
        });

        interactionList = findViewById(R.id.interactions);

        done = findViewById(R.id.done);
        back = findViewById(R.id.back);

        back.setOnClickListener(v -> finish());

        done.setOnClickListener(v -> {
            startActivity(new Intent(FoodInteractionActivity.this, MainActivity.class));
            EventBus.getDefault().post(new FinishActivityEvent(FinishActivityEvent.SPEECH));
            finish();
        });

        ingredients = getIntent().getStringArrayListExtra("ingredients");
        requestFoodInteractions(ingredients);
    }

    private void requestFoodInteractions(ArrayList<String> ingredients) {
        loadingView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);

        DrugService.getInstance().getFoodInteractionDetail(FileUtil.getSPString(this, Configs.userNameKey), ingredients)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FoodInteractionDetail>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FoodInteractionDetail value) {
                        loadingView.setVisibility(View.GONE);

                        if (value.getCode() == Configs.requestSuccess) {

                            boolean hasInteraction = false;
                            for (FoodInteraction foodInteraction : value.getInteractions()) {
                                if (foodInteraction != null && !foodInteraction.getDrugInteractions().isEmpty()) {
                                    hasInteraction = true;
                                }
                            }
                            if (!hasInteraction) {
                                // no interactions
                                noInteractionView.setVisibility(View.VISIBLE);
                                checkAgain.setVisibility(View.VISIBLE);
                            } else {
                                try {
                                    // has interactions
                                    interactionList.setVisibility(View.VISIBLE);
                                    interactionList.setLayoutManager(new LinearLayoutManager(FoodInteractionActivity.this, RecyclerView.VERTICAL, false));
                                    adapter = new FoodInteractionAdapter(FoodInteractionActivity.this, value.getInteractions());
                                    interactionList.setAdapter(adapter);

                                    // set disclaimer
                                    String disclaimerText = getResources().getString(R.string.disclaimer);
                                    SpannableString span = new SpannableString(disclaimerText);
                                    span.setSpan(new StyleSpan(Typeface.BOLD), 0, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    disclaimer.setText(span);
                                    disclaimer.setVisibility(View.VISIBLE);
                                } catch (Throwable e) {
                                    Log.e(TAG, e.getMessage());
                                }

                            }

                        } else {
                            errorView.setVisibility(View.VISIBLE);
                            loadingView.setVisibility(View.GONE);
                            Toast.makeText(FoodInteractionActivity.this, value.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        errorView.setVisibility(View.VISIBLE);
                        loadingView.setVisibility(View.GONE);
                        Log.e(TAG, e.toString());
                        Toast.makeText(FoodInteractionActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpeechUtil.destroy();
    }
}
