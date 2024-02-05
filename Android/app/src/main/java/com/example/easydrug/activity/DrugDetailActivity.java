package com.example.easydrug.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easydrug.Configs;
import com.example.easydrug.R;
import com.example.easydrug.Utils.FileUtil;
import com.example.easydrug.Utils.FinishActivityEvent;
import com.example.easydrug.Utils.RouteUtil;
import com.example.easydrug.Utils.SpeechUtil;
import com.example.easydrug.Utils.UIUtils;
import com.example.easydrug.adapter.DrugInteractionAdapter;
import com.example.easydrug.model.DrugDetail;
import com.example.easydrug.model.DrugInteraction;
import com.example.easydrug.model.GeneralResponse;
import com.example.easydrug.model.SideEffectPossibility;
import com.example.easydrug.netservice.Api.DrugService;
import com.example.easydrug.widget.ExpandTextView;
import com.example.easydrug.widget.OneButtonDialog;
import com.example.easydrug.widget.TwoButtonDialog;
import com.githang.statusbar.StatusBarCompat;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class DrugDetailActivity extends Activity {

    private String TAG = "DrugDetailActivity";
    private ExpandTextView drugDescription;
    private ImageView back;
    private RecyclerView interactionList;
    private DrugInteractionAdapter interactionAdapter;
    private TextView drugName;
    private ConstraintLayout addToList;
    private ImageView drugImage;
    private TextView disclaimer;
    private ImageView descriptionSpeaker, interactionSpeaker;
    private ConstraintLayout noDrugView, noInteractionView, loadingView, errorView, sideEffectView, descView;
    private ImageView addToListImage;
    private TextView refresh;
    private TextView sideEffectText;
    private ImageView sideEffectDefinition;
    private TextView addToListText;
    private TextView addListText;

    private String drugNameString, descriptionString, imageUrl, upc;
    private DrugDetail drugDetail;
    private int fromScene;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_detail);
        StatusBarCompat.setStatusBarColor(this, this.getResources().getColor(R.color.bg_color));

        noDrugView = findViewById(R.id.no_drug_view);
        noInteractionView = findViewById(R.id.no_interaction_view);
        loadingView = findViewById(R.id.loading_view);
        errorView = findViewById(R.id.loading_error_view);
        sideEffectView = findViewById(R.id.side_effect);
        sideEffectText = findViewById(R.id.side_effect_detail);
        sideEffectDefinition = findViewById(R.id.side_effect_definition);
        interactionList = findViewById(R.id.drug_interaction_list);
        addToListImage = findViewById(R.id.add_to_list_top);
        addToList = findViewById(R.id.add_to_list);
        addToList.setOnClickListener(addToListListener);
        addToListText = findViewById(R.id.add_to_list_text);
        addToListImage.setOnClickListener(addToListListener);
        refresh = findViewById(R.id.refresh);
        refresh.setOnClickListener(v -> requestDrugDetail());
        descView = findViewById(R.id.drug_desc);
        addToListText = findViewById(R.id.no_drug_add_list);

        addToListText.setOnClickListener(addToListListener);

        Intent intent = getIntent();
        drugNameString = intent.getStringExtra("drugName");
        descriptionString = intent.getStringExtra("drugDescription");
        imageUrl = intent.getStringExtra("drugImage");
        upc = intent.getStringExtra("upc");
        fromScene = intent.getIntExtra(Configs.drugDetailFromScene, RouteUtil.fromOther);

        drugDescription = findViewById(R.id.description_content);
        if (descriptionString == null || descriptionString.isEmpty()) {
            descView.setVisibility(View.GONE);
        } else {
            int width = UIUtils.getWidth(this) - UIUtils.dp2px(this, 72);
            drugDescription.initWidth(width);
            drugDescription.setMaxLines(7);
            drugDescription.setCloseText(descriptionString);
        }

        descriptionSpeaker = findViewById(R.id.description_speaker);
        descriptionSpeaker.setOnClickListener(v -> {
            SpeechUtil.speechText(DrugDetailActivity.this, descriptionString);
        });

        interactionSpeaker = findViewById(R.id.drug_interaction_speaker);
        interactionSpeaker.setOnClickListener(v -> {
            readInteraction();
        });

        drugName = findViewById(R.id.drug_name);
        drugName.setText(drugNameString);

        drugImage = findViewById(R.id.drug_image);
        Glide.with(this).load(imageUrl).fallback(R.drawable.drug_img_placeholder)
                .error(R.drawable.drug_img_placeholder).into(drugImage);

        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            if (fromScene == RouteUtil.fromOnBoarding) {
                startActivity(new Intent(DrugDetailActivity.this, MainActivity.class));
            } else {
                finish();
            }
        });

        if (fromScene == RouteUtil.fromDrugList) {
            addToListImage.setVisibility(View.GONE);
            addToList.setVisibility(View.GONE);
        }

        disclaimer = findViewById(R.id.disclaimer);

        requestDrugDetail();

    }

    private void requestDrugDetail() {
        loadingView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);

        DrugService.getInstance().getDrugDetail(FileUtil.getSPString(this, Configs.userNameKey), drugNameString, descriptionString, upc)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<DrugDetail>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(DrugDetail drugDetail) {
                    Log.i(TAG, drugDetail.toString());
                    if (drugDetail.getCode() == Configs.requestSuccess) {
                        loadingView.setVisibility(View.GONE);
                        DrugDetailActivity.this.drugDetail = drugDetail;

                        if (drugDetail.isDrugListEmpty()) {
                            noDrugView.setVisibility(View.VISIBLE);
                        } else {
                            noDrugView.setVisibility(View.GONE);
                            if (drugDetail.getDrugInteractions().isEmpty()) {
                                noInteractionView.setVisibility(View.VISIBLE);
                                interactionList.setVisibility(View.GONE);
                            } else {
                                noInteractionView.setVisibility(View.GONE);
                                interactionList.setVisibility(View.VISIBLE);

                                // show disclaimer
                                String disclaimerText = getResources().getString(R.string.disclaimer);
                                SpannableString span = new SpannableString(disclaimerText);
                                span.setSpan(new StyleSpan(Typeface.BOLD), 0, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                disclaimer.setText(span);
                                disclaimer.setVisibility(View.VISIBLE);

                                // interactions

                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DrugDetailActivity.this) {
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
                                interactionList.setLayoutManager(linearLayoutManager);
                                interactionAdapter = new DrugInteractionAdapter(drugDetail.getDrugInteractions(), DrugDetailActivity.this);
                                interactionList.setAdapter(interactionAdapter);
                            }
                        }

                        // side effect
                        if (drugDetail.getCurDrugSideEffect() == null || drugDetail.getCurDrugSideEffect().isEmpty()) {
                            sideEffectView.setVisibility(View.GONE);
                        } else {
                            sideEffectView.setVisibility(View.VISIBLE);
                            String sideEffectString = generateSideEffectString(drugDetail.getCurDrugSideEffect());
                            sideEffectText.setText(sideEffectString);
                            sideEffectDefinition.setOnClickListener(v -> {
                                Intent intent = new Intent(DrugDetailActivity.this, DefinitionActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("possibilities", drugDetail.getCurDrugSideEffect());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            });
                        }


                        if (drugDetail.isInList()) {
                            addToListImage.setVisibility(View.GONE);
                            addToListText.setText(R.string.view_drug_list);
                            addToList.setOnClickListener(v -> gotoDrugList());
                        }
                    } else {
                        errorView.setVisibility(View.VISIBLE);
                        loadingView.setVisibility(View.GONE);
                        Toast.makeText(DrugDetailActivity.this, drugDetail.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG, e.toString());
                    errorView.setVisibility(View.VISIBLE);
                    loadingView.setVisibility(View.GONE);
                    Toast.makeText(DrugDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onComplete() {

                }
            });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpeechUtil.destroy();
    }

    public static String generateInteractionText(ArrayList<DrugInteraction> interactions) {
        StringBuilder result = new StringBuilder();
        for (DrugInteraction interaction : interactions) {
            result.append("Interact with");
            result.append(interaction.getDrugName());
            result.append("The interaction probability is ");
            result.append(interaction.getProbability());
            result.append(".");
            result.append(interaction.getInteractionDesc());
        }
        return result.toString();
    }

    private void gotoDrugList() {
        EventBus.getDefault().post(new FinishActivityEvent(FinishActivityEvent.DRUGLIST));
        startActivity(new Intent(DrugDetailActivity.this, DrugListActivity.class));
    }

    private final View.OnClickListener addToListListener = v -> {
        if (drugDetail == null) {
            Toast.makeText(DrugDetailActivity.this, "Please wait until the drug details are loaded", Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayList<ArrayList<String>> pairs = new ArrayList<>();
        if (!drugDetail.isDrugListEmpty()) {
            pairs = drugDetail.getInteractionPairs();
        }
        DrugService.getInstance().addDrug(FileUtil.getSPString(DrugDetailActivity.this, Configs.userNameKey), drugNameString, imageUrl, upc, descriptionString, pairs)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GeneralResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GeneralResponse value) {
                        int statusRes;
                        String dialogTitle;
                        if (value.getCode() == Configs.requestSuccess) {
                            statusRes = R.drawable.dialog_correct;
                            dialogTitle = "Successfully added to list!";
                            String rightButtonText = fromScene == RouteUtil.fromOnBoarding ? "Next" : "View List";
                            new TwoButtonDialog(DrugDetailActivity.this, () -> {
                                // do nothing
                            }, () -> {
                                if (fromScene == RouteUtil.fromOnBoarding) {
                                    startActivity(new Intent(DrugDetailActivity.this, CheckListActivity.class));
                                } else {
                                    gotoDrugList();
                                    finish();
                                }
                            }).setTitle(dialogTitle)
                            .setStatusImgRes(statusRes)
                            .setLeftButtonBg(R.drawable.grey_color_stroke_bg_8dp)
                            .setLeftButtonText("Cancel")
                            .setLeftButtonTextColor(R.color.grey)
                            .setRightButtonBg(R.drawable.theme_color_bg_8dp)
                            .setRightButtonText(rightButtonText)
                            .setRightButtonTextColor(R.color.white)
                            .show();

                            addToListImage.setVisibility(View.GONE);

                            if (fromScene == RouteUtil.fromOnBoarding) {
                                addToListText.setText("Next");
                                addToList.setOnClickListener(v -> {
                                    startActivity(new Intent(DrugDetailActivity.this, CheckListActivity.class));
                                });
                            } else {
                                addToListText.setText(R.string.view_drug_list);
                                addToList.setOnClickListener(v -> {
                                    gotoDrugList();
                                    finish();
                                });
                            }

                        } else {
                            statusRes = R.drawable.dialog_error;
                            dialogTitle = value.getMsg();
                            new OneButtonDialog(DrugDetailActivity.this, () -> {
                                gotoDrugList();

                            }).setTitle(dialogTitle)
                                    .setStatusImgRes(statusRes)
                                    .show();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.toString());
                        Toast.makeText(DrugDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    };

    private void readInteraction() {
        if (loadingView.getVisibility() == View.VISIBLE) {
            SpeechUtil.speechText(DrugDetailActivity.this, this.getResources().getString(R.string.interaction_loading));
        } else if (errorView.getVisibility() == View.VISIBLE) {
            SpeechUtil.speechText(DrugDetailActivity.this, this.getResources().getString(R.string.interaction_load_error));
        } else if (noInteractionView.getVisibility() == View.VISIBLE) {
            SpeechUtil.speechText(DrugDetailActivity.this, this.getResources().getString(R.string.interaction_not_detected));
        } else if (noDrugView.getVisibility() == View.VISIBLE) {
            SpeechUtil.speechText(DrugDetailActivity.this, this.getResources().getString(R.string.interaction_no_drug));
        } else {
            SpeechUtil.speechText(DrugDetailActivity.this, generateInteractionText(drugDetail.getDrugInteractions()));
        }
    }

    private String generateSideEffectString(ArrayList<SideEffectPossibility> sideEffectPossibilities) {
        StringBuilder sideEffectString = new StringBuilder();

        for (int i = 0; i < sideEffectPossibilities.size(); i++) {
            sideEffectString.append(sideEffectPossibilities.get(i).getSideEffectName());
            sideEffectString.append("(");
            sideEffectString.append(sideEffectPossibilities.get(i).getPossibility());
            sideEffectString.append(")");

            if (i != sideEffectPossibilities.size() - 1) {
                sideEffectString.append(", ");
            }
        }

        return sideEffectString.toString();
    }

}
