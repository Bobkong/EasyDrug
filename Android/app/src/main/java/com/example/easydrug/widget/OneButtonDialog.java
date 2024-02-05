package com.example.easydrug.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.easydrug.R;

public class OneButtonDialog extends Dialog {

    private OnConfirmListener mConfirmListener;
    private TextView mTitle;
    private ConstraintLayout mConfirm;
    private ImageView mStatusImage;
    private String titleString;
    private int statusImgRes;

    public OneButtonDialog(@NonNull Context context) {
        super(context);
    }

    public OneButtonDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected OneButtonDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public OneButtonDialog(Context context, OnConfirmListener confirmListener) {
        super(context, R.style.CustomDialog);
        mConfirmListener=confirmListener;
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_button_dialog);
        mTitle = findViewById(R.id.dialog_title);
        mStatusImage = findViewById(R.id.status_image);
        mConfirm = findViewById(R.id.confirm);

        mTitle.setText(titleString);
        mStatusImage.setImageResource(statusImgRes);
        mConfirm.setOnClickListener(v -> {
            mConfirmListener.onConfirm();
            dismiss();
        });
    }

    public OneButtonDialog setTitle(String title) {
        this.titleString = title;
        return this;
    }

    public OneButtonDialog setStatusImgRes(int resId) {
        this.statusImgRes = resId;
        return this;
    }
}
