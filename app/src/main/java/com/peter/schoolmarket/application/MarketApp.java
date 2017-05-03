package com.peter.schoolmarket.application;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by PetterChen on 2017/4/10.
 */

public class MarketApp extends Application {
    private static final String TAG = "MarketApp";
    private static MarketApp instance;
    public static MarketApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initialize();
    }

    private void initialize() {
        //fresco初始化
        Fresco.initialize(this);
        //Realm初始化
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
    }
}
