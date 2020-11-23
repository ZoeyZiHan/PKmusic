package com.example.pkmusic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pkmusic.R;
import com.example.pkmusic.utils.UserUtils;
import com.example.pkmusic.views.InputView;


public class RegisterActivity extends BaseActivity {

    private InputView mInputPhone,mInputPassword,mInputPasswordConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        initNavBar(true,"注册",false);

        mInputPhone = findViewById(R.id.input_phone);
        mInputPassword = findViewById(R.id.input_password);
        mInputPasswordConfirm = findViewById(R.id.input_password_confirm);
    }

    /*
    * 1、用户输入合法性验证
    * 2、保存用户输入的信息（MD5加密之后保存）
    */
    public void onRegisterClick(View v){

        String phone = mInputPhone.getInputStr();
        String password = mInputPassword.getInputStr();
        String passwordConfirm = mInputPasswordConfirm.getInputStr();

        boolean result = UserUtils.registerUser(this,phone,password,passwordConfirm);
        if(!result) return;
        //回到登录页面
        onBackPressed();
    }

}