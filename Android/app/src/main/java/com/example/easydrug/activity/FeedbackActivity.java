package com.example.easydrug.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.easydrug.Configs;
import com.example.easydrug.R;
import com.example.easydrug.Utils.FileUtil;
import com.example.easydrug.model.GeneralResponse;
import com.example.easydrug.netservice.Api.SignService;
import com.example.easydrug.widget.OneButtonDialog;
import com.githang.statusbar.StatusBarCompat;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class FeedbackActivity extends Activity {

    private TextView submitFeedback;
    private ImageView back;
    private EditText email, title, content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        StatusBarCompat.setStatusBarColor(this, this.getResources().getColor(R.color.bg_color));

        submitFeedback = findViewById(R.id.submit_button);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        email = findViewById(R.id.email_edit);
        title = findViewById(R.id.title_edit);
        content = findViewById(R.id.content_edit);

        submitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.getText().toString().isEmpty() || title.getText().toString().isEmpty() || content.getText().toString().isEmpty()) {
                    Toast.makeText(FeedbackActivity.this, "Please make sure all fields are filled!", Toast.LENGTH_SHORT).show();
                    return;
                }
                SignService.getInstance().sendFeedback(FileUtil.getSPString(FeedbackActivity.this, Configs.userNameKey), email.getText().toString(), title.getText().toString(), content.getText().toString())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<GeneralResponse>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(GeneralResponse value) {
                                if (value.getCode() == Configs.requestSuccess) {
                                    new OneButtonDialog(FeedbackActivity.this, () -> {

                                    }).setTitle("Feedback successfully sent!")
                                            .setStatusImgRes(R.drawable.dialog_correct)
                                            .show();
                                } else {
                                    Toast.makeText(FeedbackActivity.this, value.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(FeedbackActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
    }
}
