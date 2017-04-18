package com.peter.schoolmarket.mvp.login;

/**
 * Created by PetterChen on 2017/4/11.
 */

public interface IUserModel {
    public void login(String userName, String password, OnLoginListener loginListener);
    public void register(String userName, String password, OnRegisterListener registerListener);
}
