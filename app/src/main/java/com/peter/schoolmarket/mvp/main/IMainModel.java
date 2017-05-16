package com.peter.schoolmarket.mvp.main;

import io.realm.Realm;

/**
 * Created by PetterChen on 2017/5/16.
 */

public interface IMainModel {
    void getUsers(final IMainListener listener, Realm realm);

}
