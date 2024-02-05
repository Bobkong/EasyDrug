package com.example.easydrug.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.easydrug.R;
import com.example.easydrug.activity.ScanDrugActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CustomBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private LinearLayout identityByPhoto;
    private LinearLayout searchByName;
    private ImageView cancel;
    private View divider;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_content_dialog_bottom_sheet, container, false);
        identityByPhoto = v.findViewById(R.id.identify_by_photo);
        searchByName = v.findViewById(R.id.search_by_name);
        cancel = v.findViewById(R.id.delete);
        divider = v.findViewById(R.id.divider);
        if (getActivity() != null) {
            divider.setBackgroundColor(getActivity().getResources().getColor(R.color.grey1));
        }

        identityByPhoto.setOnClickListener(v1 -> {
            startActivity(new Intent(getActivity(), ScanDrugActivity.class));
            CustomBottomSheetDialogFragment.this.dismiss();
        });

        searchByName.setOnClickListener(v12 -> {
            Toast.makeText(getActivity(), "Under development, stay tuned!", Toast.LENGTH_SHORT).show();
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomBottomSheetDialogFragment.this.dismiss();
            }
        });
        return v;
    }

}
