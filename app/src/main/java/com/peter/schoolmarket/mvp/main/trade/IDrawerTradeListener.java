package com.peter.schoolmarket.mvp.main.trade;

import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Trade;

import java.util.List;

/**
 * Created by PetterChen on 2017/5/7.
 */

public interface IDrawerTradeListener {
    void onComplete(Result<List<Trade>> result);
    void confirmComplete(Result<String> result);
    void cancelTradeComplete(Result<String> result);
    /*void onSellingComplete(Result<List<Trade>> result);
    void onBoughtComplete(Result<List<Trade>> result);
    void onSoldComplete(Result<List<Trade>> result);*/
}
