package com.example.easydrug.viewholder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easydrug.R;
import com.example.easydrug.Utils.SpeechUtil;
import com.example.easydrug.activity.DrugDetailActivity;
import com.example.easydrug.adapter.DrugInteractionAdapter;
import com.example.easydrug.model.FoodInteraction;

public class FoodInteractionViewHolder extends RecyclerView.ViewHolder {

    private FoodInteraction foodInteraction;
    private Activity activity;

    private ImageView speaker;
    private TextView foodName;
    private RecyclerView interactionList;
    private DrugInteractionAdapter adapter;

    public FoodInteractionViewHolder(@NonNull View itemView) {
        super(itemView);
        foodName = itemView.findViewById(R.id.food_name);
        speaker = itemView.findViewById(R.id.interaction_speaker);
        interactionList = itemView.findViewById(R.id.interaction_list);
    }

    public void setData(FoodInteraction foodInteraction, Activity activity) {
        this.foodInteraction = foodInteraction;
        this.activity = activity;

        foodName.setText(foodInteraction.getFoodName());
        adapter = new DrugInteractionAdapter(foodInteraction.getDrugInteractions(), activity);
        interactionList.setAdapter(adapter);

        speaker.setOnClickListener(v -> SpeechUtil.speechText(activity, DrugDetailActivity.generateInteractionText(foodInteraction.getDrugInteractions())));
    }
}
