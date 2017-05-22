package com.peter.schoolmarket.mvp.more;

import io.realm.Realm;

/**
 * Created by PetterChen on 2017/5/5.
 */

public interface IMorePresenter {
    void refresh(Realm realm);
    void init(Realm realm);
    void loadNextPage();
}
