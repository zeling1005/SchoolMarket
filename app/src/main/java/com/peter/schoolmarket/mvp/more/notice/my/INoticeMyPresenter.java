package com.peter.schoolmarket.mvp.more.notice.my;

import io.realm.Realm;

/**
 * Created by PetterChen on 2017/5/22.
 */

public interface INoticeMyPresenter {
    void initMain(Realm realm);
    void refresh();
    void loadNextPage();
}
