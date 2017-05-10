package com.peter.schoolmarket.mvp.login;

import android.widget.Toast;

import com.peter.schoolmarket.application.AppConf;
import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.User;
import com.peter.schoolmarket.mock.UserMock;
import com.peter.schoolmarket.network.NetReturn;
import com.peter.schoolmarket.network.ReqExecutor;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.MainThreadSubscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by PetterChen on 2017/4/11.
 * model中的业务类，两个业务是login和register
 */

public class UserModel implements IUserModel {

    //登录业务
    @Override
    public void login(final String userName, final String password, final OnLoginListener loginListener) {
        /*if (AppConf.useMock){
            loginListener.loginResult(new UserMock().loginOrRegisterNext());
            return;
        }*/

        final Result<User> result=new Result<User>().result(NetReturn.SERVER_ERROR);

        ReqExecutor
                .INSTANCE()
                .userReq()
                .login(userName, password)
                .subscribeOn(Schedulers.io())//在工作线程请求网络
                .observeOn(AndroidSchedulers.mainThread())//在主线程处理结果
                .subscribe(new Subscriber<Result<User>>() {

                    //此方法在onNext()方法后执行
                    @Override
                    public void onCompleted() {
                        loginListener.loginResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loginListener.loginResult(result);
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

    //注册业务
    @Override
    public void register(final String userName, final String password, final OnRegisterListener registerListener) {
        if (AppConf.useMock){
            registerListener.registerResult(new UserMock().register());
            return;
        }

        final Result<String> result = new Result<String>().result(NetReturn.SERVER_ERROR);

        ReqExecutor
                .INSTANCE()
                .userReq()
                .register(userName, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<String>>() {
                    @Override
                    public void onCompleted() {
                        registerListener.registerResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        registerListener.registerResult(result);
                    }

                    @Override
                    public void onNext(Result<String> stringResult) {
                        result.setCode(stringResult.getCode());
                        result.setMsg(stringResult.getMsg());
                        result.setData(stringResult.getData());
                    }
                });

    }
}
