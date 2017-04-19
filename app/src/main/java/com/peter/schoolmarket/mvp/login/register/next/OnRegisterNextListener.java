package com.peter.schoolmarket.mvp.login.register.next;

import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.User;

/**
 * Created by PetterChen on 2017/4/19.
 */

public interface OnRegisterNextListener {
    void registerNextResult(Result<User> result);
}
