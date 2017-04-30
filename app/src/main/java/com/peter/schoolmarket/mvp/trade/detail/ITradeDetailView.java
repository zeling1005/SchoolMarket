package com.peter.schoolmarket.mvp.trade.detail;

import com.peter.schoolmarket.data.pojo.Trade;

/**
 * Created by PetterChen on 2017/4/30.
 */

public interface ITradeDetailView {
    void showPlaceOrderSuc();
    void showPlaceOrderFail();
    void loadTradeSuccess(final Trade trade);
    void showProgress();
    void hideProgress();
}
