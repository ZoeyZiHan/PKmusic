package com.example.pkmusic.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.example.pkmusic.R;
import com.example.pkmusic.activities.LoginActivity;
import com.example.pkmusic.helps.RealmHelp;
import com.example.pkmusic.helps.UserHelp;
import com.example.pkmusic.models.UserModel;

import java.util.List;

public class UserUtils {
    //验证用户输入合法性
    public static boolean validateLogin(Context context, String phone, String password){
        if(!RegexUtils.isMobileExact(phone)){
            Toast.makeText(context, "手机号无效", Toast.LENGTH_SHORT).show();

        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }

        /*1、输入的手机号是否被注册
        * 2、手机号和密码是否正确
        * */
        if(!UserUtils.userExistFromPhone(phone)){
            Toast.makeText(context,"当前手机号未注册",Toast.LENGTH_SHORT).show();
            return false;
        }

        RealmHelp realmHelp = new RealmHelp();
        boolean result = realmHelp.validataUser(phone,EncryptUtils.encryptMD5ToString(password));

        if(!result){
            Toast.makeText(context,"手机号或密码不正确",Toast.LENGTH_SHORT).show();
            return false;
        }

        //保存用户登录标记
        boolean isSaved = SharedPreferencesUtils.saveUser(context,phone);
        if(!isSaved){
            Toast.makeText(context,"系统错误，请稍后重试",Toast.LENGTH_SHORT).show();
            return false;
        }

        UserHelp.getInstance().setPhone(phone);

        //保存音乐源数据
        realmHelp.setMusicSource(context);

        realmHelp.close();

        return true;
    }

    //退出登录
    public static void logout(Context context){

        //删除SP保存的用户标记
        boolean isRemove = SharedPreferencesUtils.removeUser(context);

        if(!isRemove){
            Toast.makeText(context,"系统错误，请稍后重试",Toast.LENGTH_SHORT).show();
            return;
        }

        //删除音乐数据源
        RealmHelp realmHelp = new RealmHelp();
        realmHelp.removeMusicSource();
        realmHelp.close();

        Intent intent = new Intent(context, LoginActivity.class);
        //添加 intent标识符，清理 task栈，并且新生成一个 task栈
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        //定义 activity跳转动画
        ((Activity)context).overridePendingTransition(R.anim.open_enter,R.anim.open_exit);
    }

    //注册用户
    public static boolean registerUser(Context context, String phone, String password, String passwordConfirm){
        if(!RegexUtils.isMobileExact(phone)){
            Toast.makeText(context, "手机号无效", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(StringUtils.isEmpty(password) || !password.equals(passwordConfirm)){
            Toast.makeText(context,"请确认密码",Toast.LENGTH_SHORT).show();
            return false;
        }

        //判断用户当前输入的手机号是否已经被注册
        /*1、通过Realm获取当前已经注册的所有用户
        * 2、根据用户输入的手机号匹配查询所有用户，如果可以匹配说明已被注册过
        */
        if(UserUtils.userExistFromPhone(phone)){
            Toast.makeText(context,"该手机号已被注册",Toast.LENGTH_SHORT).show();
            return false;
        }


        UserModel userModel = new UserModel();
        userModel.setPhone(phone);
        userModel.setPassword(EncryptUtils.encryptMD5ToString(password));
        UserUtils.saveUser(userModel);
        return true;
    }

    //保存用户到数据库
    public static void saveUser (UserModel userModel){
        RealmHelp realmHelp = new RealmHelp();
        realmHelp.saveUser(userModel);
        realmHelp.close();
    }

    //判断手机号是否存在
    public static boolean userExistFromPhone(String phone){
        boolean result = false;

        RealmHelp realmHelp = new RealmHelp();
        List<UserModel> allUser = realmHelp.getAllUser();

        for(UserModel userModel : allUser){
            if(userModel.getPhone().equals(phone)){
                result = true;
                break;
            }
        }

        realmHelp.close();
        return result;
    }

    //判断是否存在已登录用户
    public static boolean validataUserLogin(Context context){
        return SharedPreferencesUtils.isLoginUser(context);
    }

    /*
    * 修改密码
    * 1、数据验证
    *       1、原密码是否输入
    *       2、原密码是否正确
    *               1、Realm获取到当前登录的用户模型
    *               2、根据用户模型中保存的密码与输入的比较
    *       3、新密码与确定密码是否一致
    * 2、利用Realm模型自动更新特性完成密码修改
    */
    public static boolean changePassword(Context context,String oldPassword,String password,String passwordConfirm){
        if (TextUtils.isEmpty(oldPassword)) {
            Toast.makeText(context,"请输入原密码",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(password) || !password.equals(passwordConfirm)){
            Toast.makeText(context,"请确认新密码",Toast.LENGTH_SHORT).show();
            return false;
        }

        RealmHelp realmHelp = new RealmHelp();
        UserModel userModel = realmHelp.getUser();

        if(!EncryptUtils.encryptMD5ToString(oldPassword).equals(userModel.getPassword())){
            Toast.makeText(context,"原密码不正确",Toast.LENGTH_SHORT).show();
            return false;
        }

        realmHelp.changePassword(EncryptUtils.encryptMD5ToString(password));
        realmHelp.close();

        return true;
    }

}
