package com.peter.schoolmarket.mvp.find;

import io.realm.Realm;

/**
 * Created by PetterChen on 2017/4/28.
 */

public interface IFindPresenter {

    void initView(Realm realm);

    void refreshView();

    void loadNextPage();
}
