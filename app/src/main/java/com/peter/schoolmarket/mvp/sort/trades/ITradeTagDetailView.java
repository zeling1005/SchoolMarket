package com.peter.schoolmarket.mvp.sort.trades;

import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;

/**
 * Created by PetterChen on 2017/4/29.
 */

public interface ITradeTagDetailView {
    void loadDataSuccess(RecyclerCommonAdapter<?> adapter);//当数据获取成功

    void loadDataFail(String errorMsg);//当数据获取失败

    void showProgress();
    void hideProgress();
}
