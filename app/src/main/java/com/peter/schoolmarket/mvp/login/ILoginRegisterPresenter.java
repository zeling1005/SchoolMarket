package com.peter.schoolmarket.mvp.login;

/**
 * Created by PetterChen on 2017/4/11.
 */

public interface ILoginRegisterPresenter {
    //view和model的交互是登录和注册
    void login(String username,String password);
    void register(String username,String password);
}
