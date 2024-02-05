package com.example.easydrug.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easydrug.R;
import com.example.easydrug.Utils.FinishActivityEvent;
import com.example.easydrug.adapter.IngredientAdapter;
import com.example.easydrug.viewholder.IngredientViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class SpeechFoodResultActivity extends Activity {

    private String TAG = "SpeechFoodResultActivity";
    private ImageView backButton;
    private RecyclerView ingredientsView;
    private ConstraintLayout reRecord;
    private ConstraintLayout confirm;
    private IngredientAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_food_result);
        EventBus.getDefault().register(this);
        StatusBarCompat.setStatusBarColor(this, this.getResources().getColor(R.color.bg_color));
        ingredientsView = findViewById(R.id.ingredient_recycler_view);
        backButton = findViewById(R.id.back);
        backButton.setOnClickListener(v -> finish());

        reRecord = findViewById(R.id.re_record);
        reRecord.setOnClickListener(v -> finish());

        confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(v -> {
            if (adapter == null || adapter.getItemCount() == 0) {
                Toast.makeText(this, "Please input at least 1 ingredient!", Toast.LENGTH_SHORT).show();
            } else {
                ArrayList<String> ingredients = new ArrayList<>();
                for (int i = 0; i < adapter.getItemCount(); i++) {
                    IngredientViewHolder holder = (IngredientViewHolder) ingredientsView.findViewHolderForAdapterPosition(i);

                    if (holder != null) {
                        String ingredient = holder.getIngredientName();
                        if (ingredient != null && !ingredient.isEmpty()) {
                            Log.i(TAG, "ingredient: " + ingredient);
                            ingredients.add(ingredient);
                        }

                    }
                }
                Intent intent = new Intent(SpeechFoodResultActivity.this, FoodInteractionActivity.class);
                intent.putStringArrayListExtra("ingredients", ingredients);
                startActivity(intent);
            }
        });

        String ingredients = getIntent().getStringExtra("ingredients");
        String[] ingredientList = ingredients.split(",");
        ArrayList<String> mData = new ArrayList<>();
        for (String ingredient : ingredientList) {
            // remove unuseful character
            ingredient = ingredient.replace(".", "");
            if (!ingredient.isEmpty()) {
                Log.i(TAG, "add ingredient: " + ingredient);
                mData.add(ingredient);
            }
        }

        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        // 1 Recycle View set LayoutManager
        ingredientsView.setLayoutManager(flexboxLayoutManager);
        // 2 create an apapter includes all viewHolders
        adapter = new IngredientAdapter(mData, this);
        // 3 assign adapter to recycle view
        ingredientsView.setAdapter(adapter);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FinishActivityEvent event) {
        if (event.scene == FinishActivityEvent.SPEECH) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
