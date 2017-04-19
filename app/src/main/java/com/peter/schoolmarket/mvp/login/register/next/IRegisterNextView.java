package com.peter.schoolmarket.mvp.login.register.next;

/**
 * Created by PetterChen on 2017/4/19.
 */

public interface IRegisterNextView {
    //操作需要什么
    String getPhoneNumber();//取得手机号码
    //界面友好交互
    void showLoading();//显示进度框
    void hideLoading();//隐藏进度框
    //结果
    void addSuccess();//添加成功
    void addFailed();//添加失败
}
