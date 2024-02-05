package com.example.easydrug.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.easydrug.R;

public class TwoButtonDialog extends Dialog {

    private OnConfirmListener mLeftListener, mRightListener;
    private TextView mTitle;
    private ImageView mStatusImage;
    private ConstraintLayout mRightButton;
    private ConstraintLayout mLeftButton;
    private TextView mLeftButtonText;
    private TextView mRightButtonText;

    private String titleString;
    private int statusImgRes;
    private String leftButtonText, rightButtonText;
    private int leftButtonBg, rightButtonBg;
    private int leftButtonTextColor, rightButtonTextColor;

    public TwoButtonDialog(@NonNull Context context) {
        super(context);
    }

    public TwoButtonDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected TwoButtonDialog(@NonNull Context context, boolean cancelable, @Nullable DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public TwoButtonDialog(Context context, OnConfirmListener leftListener, OnConfirmListener rightListener) {
        super(context, R.style.CustomDialog);
        mLeftListener = leftListener;
        mRightListener = rightListener;
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two_button_dialog);
        mTitle = findViewById(R.id.dialog_title);
        mStatusImage = findViewById(R.id.status_image);
        mLeftButton = findViewById(R.id.left_button);
        mLeftButtonText = findViewById(R.id.left_button_text);
        mRightButton = findViewById(R.id.right_button);
        mRightButtonText = findViewById(R.id.right_button_text);

        mTitle.setText(titleString);
        mStatusImage.setImageResource(statusImgRes);
        mLeftButtonText.setText(leftButtonText);
        mRightButtonText.setText(rightButtonText);
        mLeftButton.setBackgroundResource(leftButtonBg);
        mRightButton.setBackgroundResource(rightButtonBg);
        mLeftButtonText.setTextColor(getContext().getResources().getColor(leftButtonTextColor));
        mRightButtonText.setTextColor(getContext().getResources().getColor(rightButtonTextColor));

        mLeftButton.setOnClickListener(v -> {
            mLeftListener.onConfirm();
            dismiss();
        });
        mRightButton.setOnClickListener(v -> {
            mRightListener.onConfirm();
            dismiss();
        });
    }

    public TwoButtonDialog setTitle(String title) {
        this.titleString = title;
        return this;
    }

    public TwoButtonDialog setStatusImgRes(int resId) {
        this.statusImgRes = resId;
        return this;
    }

    public TwoButtonDialog setLeftButtonText(String leftButtonText) {
        this.leftButtonText = leftButtonText;
        return this;
    }

    public TwoButtonDialog setRightButtonText(String rightButtonText) {
        this.rightButtonText = rightButtonText;
        return this;
    }

    public TwoButtonDialog setRightButtonBg(int rightButtonBg) {
        this.rightButtonBg = rightButtonBg;
        return this;
    }

    public TwoButtonDialog setLeftButtonBg(int leftButtonBg) {
        this.leftButtonBg = leftButtonBg;
        return this;
    }

    public TwoButtonDialog setLeftButtonTextColor(int leftButtonTextColor) {
        this.leftButtonTextColor = leftButtonTextColor;
        return this;
    }

    public TwoButtonDialog setRightButtonTextColor(int rightButtonTextColor) {
        this.rightButtonTextColor = rightButtonTextColor;
        return this;
    }
}
