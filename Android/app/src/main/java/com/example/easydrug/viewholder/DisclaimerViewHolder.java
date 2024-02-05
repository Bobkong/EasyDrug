package com.example.easydrug.viewholder;

import android.app.Activity;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easydrug.R;

public class DisclaimerViewHolder extends RecyclerView.ViewHolder {
    private TextView disclaimer;

    public DisclaimerViewHolder(@NonNull View itemView) {
        super(itemView);
        disclaimer = itemView.findViewById(R.id.disclaimer);

    }

    public void setData(Activity activity) {
        String disclaimerText = activity.getResources().getString(R.string.disclaimer);
        SpannableString span = new SpannableString(disclaimerText);
        span.setSpan(new StyleSpan(Typeface.BOLD), 0, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        disclaimer.setText(span);
    }
}
