package com.peter.schoolmarket.data.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.peter.schoolmarket.data.pojo.User;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by PetterChen on 2017/4/20.
 */

public class ManageLoginInfo {

    private  final String SP="userInfo";
    private static ManageLoginInfo instance;

    private ManageLoginInfo(){

    }

    //获取唯一实例函数
    public static ManageLoginInfo getInstance() {
        if (instance == null) {
            synchronized (ManageLoginInfo.class) {
                if (instance == null) {
                    instance = new ManageLoginInfo();
                }
            }
        }
        return instance;
    }

    public void saveUser(final Context context, final User user){
        SharedPreferences sp = context.getSharedPreferences(SP, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (user.getId()!=null){
            editor.putString("userId",user.getId());
        }
        if (user.getUsername()!=null){
            editor.putString("username",user.getUsername());
        }
        if (user.getPassword()!=null){
            editor.putString("password",user.getPassword());
        }
        if (user.getPhone()!=null){
            editor.putString("phone",user.getPhone());
        }
        if (user.getAvatarUrl()!=null){
            editor.putString("avatarUrl",user.getAvatarUrl());
        }
        editor.apply();
    }

    void clear(final Context context){
        SharedPreferences sp = context.getSharedPreferences(SP,MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    public User getUser(final Context context){
        SharedPreferences sp = context.getSharedPreferences(SP, MODE_PRIVATE);
        final User user=new User();
        user.setId(sp.getString("userId",""));
        user.setUsername(sp.getString("username",""));
        user.setPassword(sp.getString("password",""));
        user.setPhone(sp.getString("phone",""));
        user.setAvatarUrl(sp.getString("avatarUrl",""));
        return user;
    }
}
