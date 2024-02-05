package com.example.easydrug.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easydrug.R;
import com.example.easydrug.model.DrugInteraction;
import com.example.easydrug.viewholder.InteractionViewHolder;

import java.util.ArrayList;

public class DrugInteractionAdapter extends RecyclerView.Adapter<InteractionViewHolder> {

    private ArrayList<DrugInteraction> mData;
    private Activity activity;
    @NonNull
    @Override
    public InteractionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_interaction_detail, parent, false);
        return new InteractionViewHolder(v);
    }

    public DrugInteractionAdapter(ArrayList<DrugInteraction> data, Activity activity) {
        this.mData = data;
        this.activity = activity;
    }

    @Override
    public void onBindViewHolder(@NonNull InteractionViewHolder holder, int position) {
        holder.setData(mData.get(position), activity);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


}

