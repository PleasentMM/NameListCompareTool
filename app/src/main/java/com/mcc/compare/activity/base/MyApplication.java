package com.mcc.compare.activity.base;

import android.app.Application;

import org.litepal.LitePal;

/**
 * @CreateDate: 2020/2/7 16:28
 * @Author MCC
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(getBaseContext());
    }
}
