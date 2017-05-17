package com.peter.schoolmarket.mvp.msg;

import android.content.Context;
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
                        if (result.getCode() != 100 || data == null){
                            listener.msgReqComplete(false);
                            return;
                        }
                        /*for (int i = 0; i < data.size(); i++) {
                            final Msg msg = data.get(i);
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    int id = ManageMsgCount.getInstance().getCount(context);
                                    msg.setId(id);
                                    realm.copyToRealm(msg);
                                    ManageMsgCount.getInstance().saveCount(context, id + 1);
                                }
                            });

                        }*/
                        final RealmResults<Msg> results = realm.where(Msg.class).findAll();
                        List<Msg> msgs = realm.copyFromRealm(results);
                        if ((data.size() == msgs.size()) && msgs.containsAll(data)) {
                            listener.msgReqComplete(false);
                            Toast.makeText(context, "值相等", Toast.LENGTH_SHORT).show();
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
