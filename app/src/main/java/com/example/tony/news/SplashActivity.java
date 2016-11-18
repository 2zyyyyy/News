package com.example.tony.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //跳转到主界面
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);//映射
                startActivity(intent);
                finish();
            }
        }, 1000 * 2);//两秒后跳转
    }
}
