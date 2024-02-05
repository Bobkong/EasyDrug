package com.example.easydrug.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easydrug.R;
import com.example.easydrug.viewholder.IngredientViewHolder;

import java.util.ArrayList;

public class IngredientAdapter  extends RecyclerView.Adapter<IngredientViewHolder> {
    private ArrayList<String> mData;
    private Activity context;
    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.setData(mData.get(position), this, position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void deleteItem(int position) {
        if(mData == null || mData.isEmpty()) {
            return;
        }
        mData.remove(position);
        context.runOnUiThread(() -> notifyDataSetChanged());
    }

    public String getIngredient(int position) {
        if (mData == null || mData.size() <= position) {
            return "";
        }
        return mData.get(position);
    }


    public IngredientAdapter(ArrayList<String> data, Activity context) {
        this.mData = data;
        this.context = context;
    }
}
