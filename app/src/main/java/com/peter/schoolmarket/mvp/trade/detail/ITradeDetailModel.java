package com.peter.schoolmarket.mvp.trade.detail;

import com.peter.schoolmarket.data.pojo.Trade;
import com.peter.schoolmarket.data.pojo.User;

/**
 * Created by PetterChen on 2017/4/30.
 */

public interface ITradeDetailModel {
    void placeOrderReq(ITradeDetailListener listener, User user, int tradeId);
}
