package com.example.pkmusic.helps;


/*
* 1、用户登录
*       1、新账号第一次登录时，利用SharedPreferences保存登录用户的用户标记（在这里就是手机号）
*       2、用全局单例类UserHelp保存登录用户信息
*           1、用户登录之后
*           2、用户打开应用程序，检测SharedPreferences中是否存在登录用户标记
*               如果存在则给UserHelp赋值，并进入主页，否则进入登录页面
* 2、用户退出登录
*       删除SharedPreferences保存的用户标记，退出到登录页面
*/
public class UserHelp {

    private static UserHelp instance;

    private UserHelp(){

    }

    public static UserHelp getInstance(){
        if(instance == null){
            synchronized ((UserHelp.class)){
                if(instance == null){
                    instance = new UserHelp();
                }
            }
        }
        return instance;
    }

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
