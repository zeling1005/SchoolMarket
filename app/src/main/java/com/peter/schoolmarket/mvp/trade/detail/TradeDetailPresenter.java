package com.peter.schoolmarket.mvp.trade.detail;

import android.content.Context;

import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Trade;
import com.peter.schoolmarket.data.pojo.User;
import com.peter.schoolmarket.data.storage.LoginInfoExecutor;
import com.peter.schoolmarket.mvp.sort.trades.ITradeTagDetailListener;
import com.peter.schoolmarket.util.ResultInterceptor;

import java.util.List;

/**
 * Created by PetterChen on 2017/4/30.
 */

public class TradeDetailPresenter implements ITradeDetailPresenter, ITradeDetailListener {

    private Context context;
    private ITradeDetailView view;
    private ITradeDetailModel model;

    public TradeDetailPresenter(Context context, ITradeDetailView view) {
        this.context = context;
        this.view = view;
        this.model=new TradeDetailModel();
    }

    @Override
    public void loadTradeData(Trade trade) {
        //本来需要显示加载窗口的，但是由于数据不是从服务器来的
        view.loadTradeSuccess(trade);
    }

    @Override
    public void placeOrder(int tradeId) {
        view.showProgress();
        User user= LoginInfoExecutor.getUser(context);
        model.placeOrderReq(this, user, tradeId);
    }

    @Override
    public void placeOrderComplete(Result<String> result) {
        view.hideProgress();
        if (ResultInterceptor.instance.resultHandler(result)){
            view.showPlaceOrderReqSuc(result.getData());
        }else {
            view.showPlaceOrderReqFail();
        }
    }
}
