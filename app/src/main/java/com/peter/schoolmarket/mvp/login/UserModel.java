package com.peter.schoolmarket.mvp.login;

import android.widget.Toast;

import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.User;
import com.peter.schoolmarket.network.NetReturn;
import com.peter.schoolmarket.network.ReqExecutor;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by PetterChen on 2017/4/11.
 */

public class UserModel implements IUserModel {
    @Override
    public void login(final String userName, final String password, final OnLoginListener loginListener) {
        final Result<User> result=new Result<User>().result(NetReturn.SERVER_ERROR);
        ReqExecutor
                .INSTANCE()
                .userReq()
                .login(userName, password)
                .subscribeOn(Schedulers.io())//在工作线程请求网络
                .observeOn(AndroidSchedulers.mainThread())//在主线程处理结果
                //.subscribe(new Subscriber<Result<User>>() {
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loginListener.loginFailed();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        loginListener.loginSuccess(responseBody.toString());
                    }

                    /*@Override
                    public void onError(Throwable e) {
                        loginListener.loginFailed();
                    }

                    @Override
                    public void onNext(Result<User> resultData) {
                        result.setCode(resultData.getCode());
                        result.setMsg(resultData.getMsg());
                        result.setData(resultData.getData());
                    }*/
                });
    }

    @Override
    public void register(final String userName, final String password, final OnRegisterListener registerListener) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if ("admin".equals(userName) && "admin".equals(password)) {
                    User user = new User();
                    user.setUsername(userName);
                    user.setPassword(password);
                    registerListener.registerSuccess(user);
                } else {
                    registerListener.registerFailed();
                }
            }
        }.start();
    }
}
