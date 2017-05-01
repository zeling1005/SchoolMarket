package com.peter.schoolmarket.mvp.login;

import android.content.Context;

import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.User;
import com.peter.schoolmarket.data.storage.LoginInfoExecutor;
import com.peter.schoolmarket.network.NetReturn;

/**
 * Created by PetterChen on 2017/4/19.
 * 登录注册界面的交互器，有两个操作，登录和注册
 *
 */

public class LoginRegisterPresenter implements ILoginRegisterPresenter, OnLoginListener, OnRegisterListener {

    private IUserModel userModel;
    private ILoginRegisterView loginRegisterView;
    private Context context;

    private LoginRegisterPresenter() {

    }

    public LoginRegisterPresenter(ILoginRegisterView loginRegisterView, Context context) {
        this.context = context;
        this.userModel = new UserModel();
        this.loginRegisterView = loginRegisterView;
    }


    //登录操作：打开加载进度框，进行登录
    @Override
    public void login(String username, String password) {
        loginRegisterView.showLoginLoading();
        userModel.login(username, password, this);
    }

    //注册操作：打开加载进度框，进行注册
    @Override
    public void register(String username, String password) {
        loginRegisterView.showRegisterLoading();
        userModel.register(username, password, this);
    }

    //登录结果：隐藏加载进度框，进行返回值判断是否登录成功或者失败
    @Override
    public void loginResult(Result<User> result) {
        loginRegisterView.hideLoginLoading();

        switch (result.getCode()) {
            case 100://操作成功
                LoginInfoExecutor.logIn(context, result.getData());
                loginRegisterView.loginSuccess();
                break;
            case 99://网络异常或者系统错误
            case 201://用户名错误
            case 202://密码错误
                loginRegisterView.loginFailed(result.getMsg());
                break;
            default:
                break;
        }
    }

    //注册结果：隐藏加载进度框，进行返回值判断是否注册成功
    @Override
    public void registerResult(Result<String> result) {
        loginRegisterView.hideRegisterLoading();
        switch (result.getCode()) {
            case 100://操作成功
                loginRegisterView.registerSuccess(result.getData());
                break;
            case 99://网络异常或者系统错误
            case 203://该用户名已经存在
                loginRegisterView.registerFailed(result.getData());
                break;
            default:
                break;
        }
    }

}
