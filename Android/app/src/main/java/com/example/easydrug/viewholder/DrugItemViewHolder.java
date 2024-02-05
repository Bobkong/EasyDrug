package com.example.easydrug.viewholder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.easydrug.Configs;
import com.example.easydrug.R;
import com.example.easydrug.Utils.FileUtil;
import com.example.easydrug.Utils.FinishActivityEvent;
import com.example.easydrug.Utils.RouteUtil;
import com.example.easydrug.Utils.UIUtils;
import com.example.easydrug.activity.LoginActivity;
import com.example.easydrug.activity.ProfileActivity;
import com.example.easydrug.adapter.DrugListAdapter;
import com.example.easydrug.model.Drug;
import com.example.easydrug.model.GeneralResponse;
import com.example.easydrug.netservice.Api.DrugService;
import com.example.easydrug.widget.TwoButtonDialog;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class DrugItemViewHolder extends RecyclerView.ViewHolder {

    private ImageView drugImage, interactionImage, deleteButton;
    private TextView drugName, interactionText;
    private Drug mDrug;
    private Activity mActivity;
    private ConstraintLayout mainView;
    public DrugItemViewHolder(@NonNull View itemView) {
        super(itemView);

        drugImage = itemView.findViewById(R.id.drug_image);
        drugName = itemView.findViewById(R.id.drug_name);
        interactionImage = itemView.findViewById(R.id.interaction_logo);
        interactionText = itemView.findViewById(R.id.interaction_number);
        mainView = itemView.findViewById(R.id.main_content);
        deleteButton = itemView.findViewById(R.id.delete_button);

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mainView.getLayoutParams();
        params.width = UIUtils.getWidth(itemView.getContext()) - UIUtils.dp2px(itemView.getContext(), 32f);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrug != null && mActivity != null) {
                    RouteUtil.gotoDrugDetailScreen(mActivity, RouteUtil.fromDrugList, mDrug.getDrugName(), mDrug.getDrugDesc(), mDrug.getDrugImageUrl(), mDrug.getUpcCode());
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void setData(Activity activity, Drug drug, boolean isDeleteMode, DrugListAdapter adapter) {
        this.mDrug = drug;
        this.mActivity = activity;
        Glide.with(activity).
                load(drug.getDrugImageUrl()).
                skipMemoryCache(true)
                .fallback(R.drawable.drug_img_placeholder)
                .error(R.drawable.drug_img_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.NONE).
                into(drugImage);

        drugName.setText(drug.getDrugName());
        if (drug.getNumberOfInteractions() == 0) {
            interactionImage.setImageResource(R.drawable.dialog_correct);
            interactionText.setTextColor(activity.getResources().getColor(R.color.drug_list_interaction_green));
        } else {
            interactionImage.setImageResource(R.drawable.warning_sign);
            interactionText.setTextColor(activity.getResources().getColor(R.color.red));
        }
        interactionText.setText(drug.getNumberOfInteractions() + " interactions");

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mainView.getLayoutParams();
        if (isDeleteMode) {
            params.leftMargin = -1 * UIUtils.dp2px(itemView.getContext(), 50f);
        } else {
            params.leftMargin = UIUtils.dp2px(itemView.getContext(), 16f);
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new TwoButtonDialog(activity, () -> {
                    // do nothing
                }, () -> {
                    // remove drug
                    DrugService.getInstance().removeDrug(FileUtil.getSPString(activity, Configs.userNameKey), drug.getUpcCode(), drug.getDrugName())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<GeneralResponse>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(GeneralResponse value) {
                                    if (value.getCode() == Configs.requestSuccess) {
                                        Toast.makeText(activity, "The drug has been removed from your list", Toast.LENGTH_SHORT).show();
                                        adapter.deleteDrug(drug);
                                    } else {
                                        Toast.makeText(activity, value.getMsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }).setTitle("Are you sure to delete this drug from the list?")
                        .setStatusImgRes(R.drawable.dialog_warning)
                        .setRightButtonText("Delete")
                        .setLeftButtonText("Cancel")
                        .setLeftButtonBg(R.drawable.grey_color_stroke_bg_8dp)
                        .setRightButtonBg(R.drawable.theme_color_bg_8dp)
                        .setRightButtonTextColor(R.color.white)
                        .setLeftButtonTextColor(R.color.grey)
                        .show();

            }
        });
    }
}
