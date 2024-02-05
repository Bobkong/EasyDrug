package com.example.easydrug.viewholder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easydrug.R;
import com.example.easydrug.Utils.SpeechUtil;
import com.example.easydrug.model.SideEffectPossibility;

public class DefinitionViewHolder extends RecyclerView.ViewHolder {

    private TextView sideEffectName, sideEffectDesc;
    private ImageView speaker;
    private Activity activity;

    public DefinitionViewHolder(@NonNull View itemView) {
        super(itemView);
        sideEffectName = itemView.findViewById(R.id.side_effect_name);
        sideEffectDesc = itemView.findViewById(R.id.side_effect_desc);
        speaker = itemView.findViewById(R.id.side_effect_speaker);
        speaker.setOnClickListener(v -> {
            SpeechUtil.speechText(activity, sideEffectName.getText().toString() + " is " + sideEffectDesc.getText().toString());
        });
    }

    public void setData(SideEffectPossibility possibility, Activity activity) {
        sideEffectName.setText(possibility.getSideEffectName());
        sideEffectDesc.setText(possibility.getDefinition());
        this.activity = activity;

    }
}
