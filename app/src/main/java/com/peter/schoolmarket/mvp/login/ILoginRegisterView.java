package com.peter.schoolmarket.mvp.login;

/**
 * Created by PetterChen on 2017/4/11.
 */

public interface ILoginRegisterView {
    String getUserName();
    String getPassword();
    void showLoginLoading();
    void hideLoginLoading();
    void showRegisterLoading();
    void hideRegisterLoading();
    void LoginSuccess();
    void LoginFailed();
    void RegisterSuccess();
    void RegisterFailed();
    void clearPassword();

}
