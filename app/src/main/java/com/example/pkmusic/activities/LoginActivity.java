package com.example.pkmusic.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pkmusic.R;
import com.example.pkmusic.utils.UserUtils;
import com.example.pkmusic.views.InputView;

public class LoginActivity extends BaseActivity {

    private InputView mInputPhone,mInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    //初始化View
    private void initView(){
        initNavBar(false,"登录",false);

        mInputPhone = findViewById(R.id.input_phone);
        mInputPassword = findViewById(R.id.input_password);
    }

    //注册点击事件
    public void onRegisterClick(View v){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    //登录点击事件
    public void onCommitClick(View v){
        String phone = mInputPhone.getInputStr();
        String password = mInputPassword.getInputStr();

        //验证输入是否合法
        if(!UserUtils.validateLogin(this,phone,password)){
            return;
        }
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();

    }

}