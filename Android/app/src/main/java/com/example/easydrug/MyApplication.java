package com.example.easydrug;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.view.WindowManager;

public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        //方便一般类获取上下文
        context=getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
