package com.example.pkmusic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pkmusic.R;
import com.example.pkmusic.helps.UserHelp;
import com.example.pkmusic.utils.UserUtils;

public class MeActivity extends BaseActivity {

    private TextView mTvUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        initView();
    }

    private void initView() {
        initNavBar(true,"个人中心",false);

        mTvUser = findViewById(R.id.tv_user);
        mTvUser.setText("用户名："+ UserHelp.getInstance().getPhone());
    }

    //修改密码点击事件
    public void onChangeClick(View v){
        startActivity(new Intent(this,ChangePasswordActivity.class));
    }

    //退出登录点击事件
    public void onLogoutClick(View v){
        UserUtils.logout(this);
    }

}