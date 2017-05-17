package com.peter.schoolmarket.mvp.msg;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by PetterChen on 2017/5/17.
 */

public class ManageMsgCount {
    private  final String SP="msgCountInfo";
    private static ManageMsgCount instance;

    private ManageMsgCount(){

    }

    //获取唯一实例函数
    public static ManageMsgCount getInstance() {
        if (instance == null) {
            synchronized (ManageMsgCount.class) {
                if (instance == null) {
                    instance = new ManageMsgCount();
                }
            }
        }
        return instance;
    }

    public void saveCount(final Context context, final int count) {
        SharedPreferences sp = context.getSharedPreferences(SP, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("count", count);
        editor.apply();
    }

    public void clear(final Context context){
        SharedPreferences sp = context.getSharedPreferences(SP,MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    public int getCount(final Context context){
        SharedPreferences sp = context.getSharedPreferences(SP, MODE_PRIVATE);
        int count = sp.getInt("count", 0);
        if (count == 0) {
            count = 1;
        }
        return count;
    }
}
