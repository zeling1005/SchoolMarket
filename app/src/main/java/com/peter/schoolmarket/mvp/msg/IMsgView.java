package com.peter.schoolmarket.mvp.msg;

import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;

/**
 * Created by PetterChen on 2017/5/6.
 */

public interface IMsgView {
    void showProgress();
    void hideProgress();
    void presenterInitSuccess(RecyclerCommonAdapter<?> adapter);
    void hideRefresh();
}
