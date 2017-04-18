package com.peter.schoolmarket.mvp.login;

import com.peter.schoolmarket.data.pojo.User;

/**
 * Created by PetterChen on 2017/4/11.
 */

public interface OnRegisterListener {
    void registerSuccess(User user);
    void registerFailed();
}
