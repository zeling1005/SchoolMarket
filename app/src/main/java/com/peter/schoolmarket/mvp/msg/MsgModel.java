package com.peter.schoolmarket.mvp.msg;

import android.content.Context;
import android.icu.text.LocaleDisplayNames;
import android.util.Log;
import android.widget.Toast;

import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Msg;
import com.peter.schoolmarket.data.pojo.Order;
import com.peter.schoolmarket.network.ReqExecutor;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by PetterChen on 2017/5/6.
 */

public class MsgModel implements IMsgModel {

    @Override
    public void msgDataReq(final IMsgListener listener, final Realm realm, int userId, final Context context) {
        final Result<List<Msg>> result = new Result<>();
        ReqExecutor
                .INSTANCE()
                .userReq()
                .getMsgs(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<List<Msg>>>() {
                    @Override
                    public void onCompleted() {
                        final List<Msg> data = result.getData();
                        final RealmResults<Msg> results = realm.where(Msg.class).findAll();
                        List<Msg> msgs = realm.copyFromRealm(results);
                        /*if (!msgs.isEmpty()) {
                            Log.d("getMsgsmsgs", msgs.get(0).toString());
                        }*/
                        realm.executeTransaction(new Realm.Transaction() {//清空数据
                            @Override
                            public void execute(Realm realm) {
                                results.deleteAllFromRealm();
                            }
                        });
                        if (result.getCode()!=100 || data==null){
                            listener.msgReqComplete(false);
                            return;
                        }
                        //Async
                        realm.executeTransaction(new Realm.Transaction() {//重新加载数据
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealm(data);
                            }
                        });
                        listener.msgReqComplete(true);
                        /*if (!data.isEmpty()) {
                            Log.d("getMsgsdata", data.get(0).toString());
                        }*/
                        /*final List<Msg> data = result.getData();
                        if (result.getCode()!=100 || data==null){
                            listener.msgReqComplete(false);
                            return;
                        }

                        final RealmResults<Msg> results = realm.where(Msg.class).findAll();
                        realm.executeTransaction(new Realm.Transaction() {//清空数据
                            @Override
                            public void execute(Realm realm) {
                                results.deleteAllFromRealm();
                            }
                        });
                        realm.executeTransactionAsync(new Realm.Transaction() {//重新加载数据
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealm(data);
                            }
                        });
                        listener.msgReqComplete(true);*/

                        /*if (result.getCode() != 100) {
                            listener.msgReqComplete(false);
                            return;
                        } else if (data == null) {
                            final RealmResults<Msg> results = realm.where(Msg.class).findAll();
                            realm.executeTransaction(new Realm.Transaction() {//清空数据
                                @Override
                                public void execute(Realm realm) {
                                    results.deleteAllFromRealm();
                                }
                            });
                            listener.msgReqComplete(false);
                        } else {
                            final RealmResults<Msg> results = realm.where(Msg.class).findAll();
                            List<Msg> msgs = realm.copyFromRealm(results);
                            boolean flag = true;
                            if (msgs.size() == data.size()) {
                                for (int i = 0; i < msgs.size(); i++) {
                                    if (!msgs.get(i).getContent().equals(data.get(i).getContent())) {
                                        flag = false;
                                    }
                                }
                            }

                            if (flag) {
                                listener.msgReqComplete(false);
                            } else {
                                realm.executeTransaction(new Realm.Transaction() {//清空数据
                                    @Override
                                    public void execute(Realm realm) {
                                        results.deleteAllFromRealm();
                                    }
                                });
                                realm.executeTransactionAsync(new Realm.Transaction() {//重新加载数据
                                    @Override
                                    public void execute(Realm realm) {
                                        realm.copyToRealm(data);
                                    }
                                });
                                listener.msgReqComplete(true);
                            }
                        }*/
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onNext(Result<List<Msg>> listResult) {
                        result.setCode(listResult.getCode());
                        result.setMsg(listResult.getMsg());
                        result.setData(listResult.getData());
                    }
                });
    }
}
