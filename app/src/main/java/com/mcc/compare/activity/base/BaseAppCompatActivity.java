package com.mcc.compare.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.litepal.LitePal;

/**
 * @CreateDate: 2020/2/7 16:17
 * @Author MCC
 */
public class BaseAppCompatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LitePal.getDatabase();
    }
}
