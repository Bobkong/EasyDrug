package com.example.easydrug.viewholder;


import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.easydrug.R;
import com.example.easydrug.model.DrugInteraction;

import java.util.ArrayList;

import hbb.view.meterview.MeterView;

public class InteractionViewHolder extends RecyclerView.ViewHolder {
    TextView drugName;
    TextView description;
    TextView possibility;
    MeterView meterView;
    private DrugInteraction interaction;

    public InteractionViewHolder(View itemView) {
        super(itemView);
        drugName = itemView.findViewById(R.id.drug_name);
        description = itemView.findViewById(R.id.interaction_description);
        possibility = itemView.findViewById(R.id.possibility_num);
        meterView = itemView.findViewById(R.id.meter_view);

    }

    public void setData(DrugInteraction interaction, Activity activity) {
        this.interaction = interaction;
        drugName.setText(interaction.getDrugName());
        description.setText(interaction.getInteractionDesc());
        possibility.setText(interaction.getProbability());
        int possibilityValue = (int) Float.parseFloat(interaction.getProbability().replace("%", ""));
        meterView.getDataManager().setRate(50);
        meterView.getDataManager().setSourceCurveColor(activity.getResources().getColor(getPossibilityColor(possibilityValue)));
        meterView.getDataManager().setPointColor(activity.getResources().getColor(getPossibilityColor(possibilityValue)));
        meterView.runSource(possibilityValue);
    }

    public int getPossibilityColor(int possibility) {
        if (possibility <= 30) {
            return R.color.possibility_30;
        } else if (possibility > 70) {
            return R.color.red;
        } else {
            return R.color.possibility_30_70;
        }
    }
}