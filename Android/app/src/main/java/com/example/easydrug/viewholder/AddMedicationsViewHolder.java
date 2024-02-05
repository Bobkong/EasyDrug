package com.example.easydrug.viewholder;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easydrug.R;
import com.example.easydrug.activity.ScanDrugActivity;
import com.example.easydrug.fragment.CustomBottomSheetDialogFragment;

public class AddMedicationsViewHolder extends RecyclerView.ViewHolder {

    private TextView addMedication;
    public AddMedicationsViewHolder(@NonNull View itemView) {
        super(itemView);

        addMedication = itemView.findViewById(R.id.add_medications);
    }

    public void setData(FragmentActivity activity) {
        addMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, ScanDrugActivity.class));
            }
        });
    }
}
