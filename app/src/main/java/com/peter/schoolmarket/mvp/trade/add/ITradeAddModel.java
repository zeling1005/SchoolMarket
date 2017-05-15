package com.peter.schoolmarket.mvp.trade.add;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by PetterChen on 2017/4/30.
 */

public interface ITradeAddModel {
    void addTradeReq(final RequestBody tradeBody, final RequestBody photoBody,
                     ITradeAddListener listener);
}
