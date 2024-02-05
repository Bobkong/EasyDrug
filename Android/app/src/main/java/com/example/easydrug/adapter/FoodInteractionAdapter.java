package com.example.easydrug.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easydrug.R;
import com.example.easydrug.model.FoodInteraction;
import com.example.easydrug.model.ResourcesContent;
import com.example.easydrug.viewholder.DisclaimerViewHolder;
import com.example.easydrug.viewholder.FoodInteractionViewHolder;

import java.util.ArrayList;
import java.util.Iterator;

public class FoodInteractionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private ArrayList<FoodInteraction> mData;

    private int Type_interaction = 0;
    private int Type_disclaimer = 1;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Type_interaction) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_interaction, parent, false);
            return new FoodInteractionViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_disclaimer, parent, false);
            return new DisclaimerViewHolder(v);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FoodInteractionViewHolder) {
            ((FoodInteractionViewHolder) holder).setData(mData.get(position), activity);
        } else if (holder instanceof DisclaimerViewHolder) {
            ((DisclaimerViewHolder) holder).setData(activity);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    public FoodInteractionAdapter(Activity activity, ArrayList<FoodInteraction> interactions) {
        this.activity = activity;
        for (Iterator<FoodInteraction> iterator = interactions.iterator(); iterator.hasNext(); ) {
            FoodInteraction value = iterator.next();
            if (value.getDrugInteractions() == null || value.getDrugInteractions().isEmpty()) {
                iterator.remove();
            }
        }
        this.mData = interactions;

    }

    @Override
    public int getItemViewType(int position) {
        if (position < mData.size()) {
            return Type_interaction;
        } else {
            return Type_disclaimer;
        }
    }
}
