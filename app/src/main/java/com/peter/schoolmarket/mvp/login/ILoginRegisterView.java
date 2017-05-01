package com.peter.schoolmarket.mvp.login;

/**
 * Created by PetterChen on 2017/4/11.
 */

public interface ILoginRegisterView {
    //界面友好交互
    void showLoginLoading();//登录进度框
    void hideLoginLoading();//隐藏登录进度框
    void showRegisterLoading();//注册进度框
    void hideRegisterLoading();//隐藏进度框
    //结果
    void loginSuccess();//登录成功
    void loginFailed(String msg);//登录失败
    void registerSuccess(String userId);//注册成功
    void registerFailed(String msg);//注册失败
}
