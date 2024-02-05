package com.example.easydrug.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.easydrug.Configs;
import com.example.easydrug.R;
import com.example.easydrug.Utils.FileUtil;
import com.example.easydrug.Utils.PasswordEncryptUtil;
import com.example.easydrug.model.GeneralResponse;
import com.example.easydrug.netservice.Api.SignService;
import com.githang.statusbar.StatusBarCompat;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class LoginActivity extends Activity {
    private String TAG = "LoginActivity";
    private ConstraintLayout logIn;
    private LinearLayout signUp;
    EditText username, password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarCompat.setStatusBarColor(this, this.getResources().getColor(R.color.bg_color));
        username = findViewById(R.id.editText1);
        password = findViewById(R.id.editText2);
        logIn = findViewById(R.id.login_button);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignService.getInstance().signIn(username.getText().toString(), PasswordEncryptUtil.INSTANCE.encrypt(password.getText().toString())).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<GeneralResponse>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(GeneralResponse value) {
                                if (value.getCode() == Configs.requestSuccess) {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    // save to local db
                                    FileUtil.saveSPString(LoginActivity.this, Configs.userNameKey, username.getText().toString());
                                    FileUtil.saveSPString(LoginActivity.this, Configs.passwordKey, PasswordEncryptUtil.INSTANCE.encrypt(password.getText().toString()));
                                    FileUtil.saveSPBool(LoginActivity.this, Configs.ifSignedUpKey, true);
                                    finish();
                                } else {
                                    // toast
                                    Toast.makeText(getApplicationContext(), value.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                // fail
                                Log.e(TAG, e.toString());
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });

        signUp = findViewById(R.id.signup_button);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

}
