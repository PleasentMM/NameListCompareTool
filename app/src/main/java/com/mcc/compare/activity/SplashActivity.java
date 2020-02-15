package com.mcc.compare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mcc.compare.R;
import com.mcc.compare.activity.base.BaseAppCompatActivity;

public class SplashActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                SplashActivity.this.finish();
            }
        },2000);
    }
}
