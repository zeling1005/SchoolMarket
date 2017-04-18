package com.peter.schoolmarket.mvp.login;

/**
 * Created by PetterChen on 2017/4/11.
 */

public interface ILoginRegisterPresenter {
    void login(String username,String password);
    void register(String username,String password);
}
