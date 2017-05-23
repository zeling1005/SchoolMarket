package com.peter.schoolmarket.mvp.more;

import io.realm.Realm;

/**
 * Created by PetterChen on 2017/5/5.
 */

public interface IMoreModel {
    void noticeDataReq(final IMoreListener listener,final int page,final Realm realm);
    void searchDataReq(final IMoreListener listener,final String query);
}
