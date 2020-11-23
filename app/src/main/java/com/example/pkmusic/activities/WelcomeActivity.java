package com.example.pkmusic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.pkmusic.R;
import com.example.pkmusic.utils.UserUtils;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

//延迟三秒自动跳转
public class WelcomeActivity extends BaseActivity{

    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        init();
    }

    private void init() {

        final boolean isLogin = UserUtils.validataUserLogin(this);

        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(isLogin){
                    toMain();
                }else{
                    toLogin();
                }

            }
        },3000);
    }

    private  void toLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private  void toMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}