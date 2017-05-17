package com.peter.schoolmarket.mvp.main.trade;

import com.peter.schoolmarket.mvp.sort.trades.ITradeTagDetailListener;

/**
 * Created by PetterChen on 2017/5/7.
 */

public interface IDrawerTradeModel {
    void drawerTradeDataReq(final IDrawerTradeListener listener, final int typeId, final int page, int myId);
    void confirmReceiveTrade(final IDrawerTradeListener listener, final int myId, final int tradeId);
    void confirmReceiveMoney(final IDrawerTradeListener listener, final int myId, final int tradeId);
    void cancelOrder(final IDrawerTradeListener listener, final int tradeId);
    /*void sellingDataReq(final IDrawerTradeListener listener, final String tagName, final int page);
    void boughtDataReq(final IDrawerTradeListener listener, final String tagName, final int page);
    void soldDataReq(final IDrawerTradeListener listener, final String tagName, final int page);*/

}
