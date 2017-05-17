package com.peter.schoolmarket.mvp.msg;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.peter.schoolmarket.application.AppConf;
import com.peter.schoolmarket.data.storage.LoginInfoExecutor;

import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by PetterChen on 2017/5/17.
 */

public class MsgService extends Service implements IMsgListener {
    public static final String ACTION = "com.peter.schoolmarket.mvp.msg.MsgService";
    private Realm realm;
    private IMsgModel model;
    private Subscription ms;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        realm=Realm.getDefaultInstance();
        model=new MsgModel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final int userId = LoginInfoExecutor.getUser(this).getId();
        ms = Observable.interval(1L, AppConf.Msg_Interval, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        model.msgDataReq(MsgService.this, realm, userId, MsgService.this);
                    }
                });
        return START_NOT_STICKY;//如果在执行完onStartCommand后，服务被异常kill掉，系统不会自动重启该服务。
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        ms.unsubscribe();
        realm.close();
    }

    @Override
    public void msgReqComplete(boolean flag) {
        /*if (flag) {
            Log.d("getMsgsmsgs", "Service发送广播");
            Intent intent = new Intent(MsgFragment.MY_BROADCAST_ACTION);
            sendBroadcast(intent);
        }*/
        Intent intent = new Intent(MsgFragment.MY_BROADCAST_ACTION);
        sendBroadcast(intent);
    }
}
