package com.peter.schoolmarket.mvp.trade.add;

import com.peter.schoolmarket.data.dto.Result;

/**
 * Created by PetterChen on 2017/4/30.
 */

public interface ITradeAddListener {
    void addTradeResult(Result<String> result);//执行发布商品请求，得到请求结果
}
