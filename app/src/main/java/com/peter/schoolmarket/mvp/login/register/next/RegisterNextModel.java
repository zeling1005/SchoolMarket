package com.peter.schoolmarket.mvp.login.register.next;

import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.User;
import com.peter.schoolmarket.network.NetReturn;
import com.peter.schoolmarket.network.ReqExecutor;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by PetterChen on 2017/4/19.
 */

public class RegisterNextModel implements IRegisterNextModel {
    @Override
    public void addPhoneNumber(String phoneNumber, final String userId, final OnRegisterNextListener onRegisterNextListener) {

        final Result<User> result=new Result<User>().result(NetReturn.SERVER_ERROR);

        //此网络请求等服务器搭好再调整
        ReqExecutor
                .INSTANCE()
                .userReq()
                .registerNext(userId, phoneNumber)
                .subscribeOn(Schedulers.io())//在工作线程请求网络
                .observeOn(AndroidSchedulers.mainThread())//在主线程处理结果
                .subscribe(new Subscriber<Result<User>>() {

                    //此方法在onNext()方法后执行
                    @Override
                    public void onCompleted() {
                        onRegisterNextListener.registerNextResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onRegisterNextListener.registerNextResult(result);
                    }

                    //相当与onClick()
                    @Override
                    public void onNext(Result<User> resultData) {
                        result.setCode(resultData.getCode());
                        result.setMsg(resultData.getMsg());
                        result.setData(resultData.getData());
                    }
                });
    }
}
