package com.peter.schoolmarket.mvp.main;

import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.User;

import java.util.List;

import io.realm.Realm;

/**
 * Created by PetterChen on 2017/5/16.
 */

public interface IMainListener {
    void onUsersReqComplete(Result<List<User>> result, Realm realm);
}
