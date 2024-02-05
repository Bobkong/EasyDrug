package com.example.easydrug.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easydrug.R;
import com.example.easydrug.model.SideEffectPossibility;
import com.example.easydrug.viewholder.DefinitionViewHolder;

import java.util.ArrayList;

public class DefinitionAdapter extends RecyclerView.Adapter<DefinitionViewHolder> {

    private ArrayList<SideEffectPossibility> mData;
    private Activity mActivity;
    @NonNull
    @Override
    public DefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_definition, parent, false);
        return new DefinitionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DefinitionViewHolder holder, int position) {
        holder.setData(mData.get(position), mActivity);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public DefinitionAdapter(ArrayList<SideEffectPossibility> data, Activity activity) {
        this.mData = data;
        this.mActivity = activity;
    }
}
