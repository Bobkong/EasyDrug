package com.example.easydrug.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.easydrug.R;
import com.example.easydrug.Utils.UIUtils;
import com.example.easydrug.adapter.ContentAdapter;
import com.example.easydrug.model.ResourcesContent;
import com.example.easydrug.widget.RoundedCornersTransform;

public class ResourceContentViewHolder extends RecyclerView.ViewHolder {
//    private TextView tagName;
    private ContentAdapter adapter;
    private int position;
    private String TAG = "ContentViewHolder";
    private TextView title, desc, isNew, contentType;
    private ImageView contentImage;
    private Activity activity;
    private ConstraintLayout rootView;

    public ResourceContentViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        desc = itemView.findViewById(R.id.desc);
        contentImage = itemView.findViewById(R.id.content_image);
        isNew = itemView.findViewById(R.id.is_new_id);
        contentType = itemView.findViewById(R.id.content_type);
        rootView = itemView.findViewById(R.id.root_view);
    }

    public void setData(ResourcesContent content, ContentAdapter adapter, int position, Activity activity) {
        title.setText(content.getName());
        desc.setText(content.getDesc());
        RoundedCornersTransform transform = new RoundedCornersTransform(activity, UIUtils.dp2px(activity, 20f));
        transform.setNeedCorner(true, true, false, false);
        Glide.with(activity).
                load(content.getImage_url()).
                skipMemoryCache(true).
                diskCacheStrategy(DiskCacheStrategy.NONE).
                transform(transform).
                into(contentImage);

        this.adapter = adapter;
        this.position = position;
        if (content.isIs_new()) {
            isNew.setVisibility(View.VISIBLE);
        } else {
            isNew.setVisibility(View.GONE);
        }

        contentType.setText(content.getType());

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(content.getSource());
                activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });
    }

}
