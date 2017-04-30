package com.peter.schoolmarket.mvp.trade.detail;

import com.peter.schoolmarket.data.dto.Result;

/**
 * Created by PetterChen on 2017/4/30.
 */

public interface ITradeDetailListener {
    void placeOrderComplete(Result<String> result);
}
