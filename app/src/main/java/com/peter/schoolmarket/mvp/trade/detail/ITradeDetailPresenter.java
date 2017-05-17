package com.peter.schoolmarket.mvp.trade.detail;

import com.peter.schoolmarket.data.pojo.Trade;

/**
 * Created by PetterChen on 2017/4/30.
 */

public interface ITradeDetailPresenter {
    void loadTradeData(final Trade trade);
    void placeOrder(int tradeId);
}
