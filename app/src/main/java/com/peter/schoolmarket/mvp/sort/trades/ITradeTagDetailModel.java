package com.peter.schoolmarket.mvp.sort.trades;

/**
 * Created by PetterChen on 2017/4/29.
 */

public interface ITradeTagDetailModel {
    void tradesDataReq(final ITradeTagDetailListener listener, final String tagName, final int page);
    void searchDataReq(final ITradeTagDetailListener listener, final String query, final String tagName);
}
