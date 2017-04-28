package com.peter.schoolmarket.mvp.find;

import java.util.List;

import io.realm.Realm;

/**
 * Created by PetterChen on 2017/4/28.
 */

public interface IFindModel {
    void executeGetTradesReq(IGainListener listener,String schoolName,int page,Realm realm);
}
