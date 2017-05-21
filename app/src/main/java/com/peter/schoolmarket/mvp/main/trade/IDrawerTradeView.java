package com.peter.schoolmarket.mvp.main.trade;

import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;

/**
 * Created by PetterChen on 2017/5/7.
 */

public interface IDrawerTradeView {
    void loadDataSuccess(RecyclerCommonAdapter<?> adapter);//当数据获取成功
    void loadDataFail(String errorMsg);//当数据获取失败
    void showRefresh();
    void hideRefresh();
    void showProgress();
    void hideProgress();
    void onSuccess(String msg);
    void onFail(String msg);
}
