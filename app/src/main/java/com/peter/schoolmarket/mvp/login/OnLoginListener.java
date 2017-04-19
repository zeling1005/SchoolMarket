package com.peter.schoolmarket.mvp.login;

import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.User;

/**
 * Created by PetterChen on 2017/4/11.
 * 登录结果监听
 */

public interface OnLoginListener {
    void loginResult(Result<User> result);
}
