package com.peter.schoolmarket.mvp.login;

import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.User;

/**
 * Created by PetterChen on 2017/4/11.
 * 注册结果监听
 */

public interface OnRegisterListener {
    void registerResult(Result<String> result);
}
