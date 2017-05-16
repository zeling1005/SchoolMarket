package com.peter.schoolmarket.mvp.find;

import java.util.List;

import io.realm.Realm;

/**
 * Created by PetterChen on 2017/4/28.
 */

public interface IFindModel {
    void tradesDataReq(final IGainListener listener,final int page);
}
