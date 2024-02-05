package com.example.easydrug.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easydrug.R;
import com.example.easydrug.model.Drug;
import com.example.easydrug.viewholder.AddMedicationsViewHolder;
import com.example.easydrug.viewholder.DisclaimerViewHolder;
import com.example.easydrug.viewholder.DrugItemViewHolder;
import com.example.easydrug.viewholder.FoodInteractionViewHolder;

import java.util.ArrayList;

public class DrugListAdapter extends RecyclerView.Adapter {
    private ArrayList<Drug> drugList;
    private FragmentActivity activity;
    private boolean isDeleteMode;

    private int Type_drug = 0;
    private int Type_add = 1;

    public DrugListAdapter(FragmentActivity activity, ArrayList<Drug> drugList) {
        this.drugList = drugList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Type_drug) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drug, parent, false);
            return new DrugItemViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_medications, parent, false);
            return new AddMedicationsViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DrugItemViewHolder) {
            ((DrugItemViewHolder) holder).setData(activity, drugList.get(position), isDeleteMode, this);
        } else if (holder instanceof AddMedicationsViewHolder) {
            ((AddMedicationsViewHolder) holder).setData(activity);
        }
    }

    @Override
    public int getItemCount() {
        return drugList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < drugList.size()) {
            return Type_drug;
        } else {
            return Type_add;
        }
    }

    public void setDeleteMode(boolean isDeleteMode) {
        this.isDeleteMode = isDeleteMode;
        notifyDataSetChanged();
    }

    public void deleteDrug(Drug drug) {
        drugList.remove(drug);
        notifyDataSetChanged();
    }
}
