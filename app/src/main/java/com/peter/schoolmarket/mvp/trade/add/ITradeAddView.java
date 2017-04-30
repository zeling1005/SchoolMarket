package com.peter.schoolmarket.mvp.trade.add;

import java.util.List;

/**
 * Created by PetterChen on 2017/4/30.
 */

public interface ITradeAddView {
    void tradeAddSuccess();//发布商品成功

    void whenFail(String errorMsg);//发生异常

    void showProgress();//显示进度条

    void hideProgress();//隐藏进度条
}
