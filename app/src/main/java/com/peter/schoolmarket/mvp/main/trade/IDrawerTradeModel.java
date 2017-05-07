package com.peter.schoolmarket.mvp.main.trade;

import com.peter.schoolmarket.mvp.sort.trades.ITradeTagDetailListener;

/**
 * Created by PetterChen on 2017/5/7.
 */

public interface IDrawerTradeModel {
    void drawerTradeDataReq(final IDrawerTradeListener listener, final int typeId, final int page);
    /*void sellingDataReq(final IDrawerTradeListener listener, final String tagName, final int page);
    void boughtDataReq(final IDrawerTradeListener listener, final String tagName, final int page);
    void soldDataReq(final IDrawerTradeListener listener, final String tagName, final int page);*/

}
