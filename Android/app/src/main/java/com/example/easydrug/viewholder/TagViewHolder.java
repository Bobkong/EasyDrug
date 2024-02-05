package com.example.easydrug.viewholder;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easydrug.R;
import com.example.easydrug.Utils.UIUtils;
import com.example.easydrug.activity.ExploreActivity;
import com.example.easydrug.adapter.TagAdapter;

public class TagViewHolder extends RecyclerView.ViewHolder{
    private TagAdapter adapter;
    private int position;
    private String TAG = "TagViewHolder";
    private TextView tagNameTextView;

    public TagViewHolder(@NonNull View itemView) {
        super(itemView);
        tagNameTextView = itemView.findViewById(R.id.tag_name);
    }

    public void setData(String title, TagAdapter adapter, int position, Activity activity, final boolean isSelected) {
        this.tagNameTextView.setText(title);
        this.adapter = adapter;
        this.position = position;
        if (position == 0) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) this.tagNameTextView.getLayoutParams();
            params.leftMargin = UIUtils.dp2px(activity, 16f);
        }

        if (isSelected) {
            tagNameTextView.setBackgroundResource(R.drawable.light_orange_bg);
            tagNameTextView.setTextColor(Color.WHITE);
        } else {
            tagNameTextView.setBackgroundResource(R.drawable.orange_bg);
            tagNameTextView.setTextColor(Color.BLACK);
        }

        this.tagNameTextView.setOnClickListener(v -> {
            if (isSelected) {
                adapter.setSelectedIndex(-1);
                ((ExploreActivity) activity).resetTag();
            } else {
                adapter.setSelectedIndex(position);
                ((ExploreActivity) activity).selectTag(title);
            }
            adapter.notifyDataSetChanged();
        });
    }
}


