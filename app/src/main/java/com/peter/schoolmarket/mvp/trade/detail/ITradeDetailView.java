package com.peter.schoolmarket.mvp.trade.detail;

import com.peter.schoolmarket.data.pojo.Trade;

/**
 * Created by PetterChen on 2017/4/30.
 */

public interface ITradeDetailView {
    void showPlaceOrderReqSuc(String msg);
    void showPlaceOrderReqFail();
    void loadTradeSuccess(final Trade trade);
    void showProgress();
    void hideProgress();
}
