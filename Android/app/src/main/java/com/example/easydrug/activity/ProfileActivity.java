package com.example.easydrug.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.easydrug.Configs;
import com.example.easydrug.R;
import com.example.easydrug.Utils.FileUtil;
import com.example.easydrug.Utils.FinishActivityEvent;
import com.example.easydrug.Utils.PasswordEncryptUtil;
import com.example.easydrug.Utils.UIUtils;
import com.example.easydrug.Utils.UpdateProfileEvent;
import com.example.easydrug.model.GeneralResponse;
import com.example.easydrug.netservice.Api.SignService;
import com.example.easydrug.widget.TwoButtonDialog;
import com.githang.statusbar.StatusBarCompat;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class ProfileActivity extends Activity {

    private LinearLayout editProfile, logout, contactUs;
    private TextView username;
    private ImageView back;
    private ConstraintLayout editProfileView, profileList;
    private EditText usernameEdit, passwordEdit;
    private ConstraintLayout saveButton;
    private ImageView showPassword;
    private boolean isShowPassword = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.bg_color));

        editProfile = findViewById(R.id.edit_profile);
        logout = findViewById(R.id.log_out);
        contactUs = findViewById(R.id.contact_us);
        back = findViewById(R.id.back);
        editProfileView = findViewById(R.id.edit_view);
        usernameEdit = findViewById(R.id.username_edit);
        passwordEdit = findViewById(R.id.password_edit);
        saveButton = findViewById(R.id.save_button);
        showPassword = findViewById(R.id.show_password);
        profileList = findViewById(R.id.profile_list);
        username = findViewById(R.id.username);
        username.setText(FileUtil.getSPString(ProfileActivity.this, Configs.userNameKey));

        back.setOnClickListener(v -> {
            if (editProfileView.getVisibility() == View.VISIBLE) {
                editProfileView.setVisibility(View.GONE);
                profileList.setVisibility(View.VISIBLE);
                isShowPassword = false;
                showPassword.setImageResource(R.drawable.hide_password);
            } else {
                finish();
            }
        });

        editProfile.setOnClickListener(v -> {
            profileList.setVisibility(View.GONE);
            editProfileView.setVisibility(View.VISIBLE);
            usernameEdit.setText(FileUtil.getSPString(ProfileActivity.this, Configs.userNameKey));
            String password = PasswordEncryptUtil.INSTANCE.decrypt(FileUtil.getSPString(ProfileActivity.this, Configs.passwordKey));
            passwordEdit.setText(password);
            passwordEdit.setInputType(129);
        });

        showPassword.setOnClickListener(v -> {
            if (isShowPassword) {
                int padding = UIUtils.dp2px(this, 10f);
                showPassword.setPadding(padding, padding, padding, padding);
                showPassword.setImageResource(R.drawable.hide_password);
                passwordEdit.setInputType(129);
                isShowPassword = false;
            } else {
                showPassword.setImageResource(R.drawable.see_password);
                int padding = UIUtils.dp2px(this, 8f);
                showPassword.setPadding(padding, padding, padding, padding);
                passwordEdit.setInputType(128);
                isShowPassword = true;
            }
        });

        saveButton.setOnClickListener(v -> {
            if (usernameEdit.getText().toString().equals(FileUtil.getSPString(ProfileActivity.this, Configs.userNameKey))
            && passwordEdit.getText().toString().equals(PasswordEncryptUtil.INSTANCE.decrypt(FileUtil.getSPString(ProfileActivity.this, Configs.passwordKey)))) {
                Toast.makeText(this, "Nothing changes!", Toast.LENGTH_SHORT).show();
            } else {
                SignService.getInstance().updateProfile(FileUtil.getSPString(ProfileActivity.this, Configs.userNameKey),
                        usernameEdit.getText().toString(), PasswordEncryptUtil.INSTANCE.encrypt(passwordEdit.getText().toString()))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<GeneralResponse>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(GeneralResponse value) {
                                if (value.getCode() == Configs.requestSuccess) {
                                    FileUtil.saveSPString(ProfileActivity.this, Configs.userNameKey, usernameEdit.getText().toString());
                                    FileUtil.saveSPString(ProfileActivity.this, Configs.passwordKey, PasswordEncryptUtil.INSTANCE.encrypt(passwordEdit.getText().toString()));
                                    username.setText(usernameEdit.getText().toString());
                                    EventBus.getDefault().post(new UpdateProfileEvent());
                                    Toast.makeText(ProfileActivity.this, "Successfully Saved!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ProfileActivity.this, value.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });

        logout.setOnClickListener(v -> {
            new TwoButtonDialog(this, () -> {
                // do nothing
            }, () -> {
                // delete profile & go to login
                FileUtil.deleteSPString(ProfileActivity.this, Configs.userNameKey);
                FileUtil.deleteSPString(ProfileActivity.this, Configs.passwordKey);
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                finish();
                EventBus.getDefault().post(new FinishActivityEvent(FinishActivityEvent.HOME));
            }).setTitle(getString(R.string.log_out_title))
                    .setStatusImgRes(R.drawable.dialog_warning)
                    .setRightButtonText("Log Out")
                    .setLeftButtonText("Cancel")
                    .setRightButtonBg(R.drawable.grey_color_stroke_bg_8dp)
                    .setLeftButtonBg(R.drawable.theme_color_bg_8dp)
                    .setLeftButtonTextColor(R.color.white)
                    .setRightButtonTextColor(R.color.grey)
                    .show();
        });

    }
}
