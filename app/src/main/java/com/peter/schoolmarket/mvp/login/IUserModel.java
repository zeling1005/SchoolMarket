package com.peter.schoolmarket.mvp.login;

/**
 * Created by PetterChen on 2017/4/11.
 */

public interface IUserModel {
    void login(String userName, String password, OnLoginListener loginListener);
    void register(String userName, String password, OnRegisterListener registerListener);
}
