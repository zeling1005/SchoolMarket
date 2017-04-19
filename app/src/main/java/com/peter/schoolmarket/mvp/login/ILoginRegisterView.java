package com.peter.schoolmarket.mvp.login;

/**
 * Created by PetterChen on 2017/4/11.
 */

public interface ILoginRegisterView {
    //操作需要什么
    String getUserName();//取得用户名
    String getPassword();//取得登录密码
    //界面友好交互
    void showLoginLoading();//登录进度框
    void hideLoginLoading();//隐藏登录进度框
    void showRegisterLoading();//注册进度框
    void hideRegisterLoading();//隐藏进度框
    //结果
    void loginSuccess();//登录成功
    void loginFailed();//登录失败
    void registerSuccess();//注册成功
    void registerFailed();//注册失败
    //重新登录或注册时候需要
    void clearPassword();//清空密码

}
