package com.peter.schoolmarket.util;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by PetterChen on 2017/4/10.
 * Realm工具类，用于初始化Realm什么的
 */

public class RealmUtils {
    /*private Context appContext;
    //realm唯一实例
    private static RealmUtils instance;
    //realm名字
    private String realmName = "marketRealm.realm";

    private RealmUtils(Context appContext) {
        this.appContext = appContext;
    }

    //获取唯一实例函数
    public static RealmUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (RealmUtils.class) {
                if (instance == null) {
                    instance = new RealmUtils(context);
                }
            }
        }
        return instance;
    }

    public Realm getRealm() {
        return Realm.getInstance(new RealmConfiguration.Builder(appContext).name(realmName).build());
    }*/
}
