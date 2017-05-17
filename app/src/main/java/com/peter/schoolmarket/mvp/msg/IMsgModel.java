package com.peter.schoolmarket.mvp.msg;

import android.content.Context;

import io.realm.Realm;

/**
 * Created by PetterChen on 2017/5/6.
 */

public interface IMsgModel {
    void msgDataReq(IMsgListener listener, Realm realm, int userId, final Context context);
}
