package com.peter.schoolmarket.mvp.main;

import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.User;
import com.peter.schoolmarket.network.NetReturn;
import com.peter.schoolmarket.network.ReqExecutor;

import java.util.List;

import io.realm.Realm;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by PetterChen on 2017/5/16.
 */

public class MainModel implements IMainModel {
    @Override
    public void getUsers(final IMainListener listener, final Realm realm) {
        final Result<List<User>> result = new Result<List<User>>().result(NetReturn.SERVER_ERROR);
        ReqExecutor
                .INSTANCE()
                .userReq()
                .getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<List<User>>>() {
                    @Override
                    public void onCompleted() {
                        listener.onUsersReqComplete(result, realm);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onUsersReqComplete(result, realm);
                    }
                    @Override
                    public void onNext(Result<List<User>> listResult) {
                        result.setCode(listResult.getCode());
                        result.setMsg(listResult.getMsg());
                        result.setData(listResult.getData());
                    }
                });
    }
}
