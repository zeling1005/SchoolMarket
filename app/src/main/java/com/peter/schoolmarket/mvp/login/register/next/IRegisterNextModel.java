package com.peter.schoolmarket.mvp.login.register.next;

/**
 * Created by PetterChen on 2017/4/19.
 */

public interface IRegisterNextModel {
    void addPhoneNumber(String phoneNumber, String userId, OnRegisterNextListener onRegisterNextListener);
}
