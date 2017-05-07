package com.peter.schoolmarket.mvp.main.trade;

import com.peter.schoolmarket.application.AppConf;
import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Trade;
import com.peter.schoolmarket.mock.TradeMock;
import com.peter.schoolmarket.network.NetReturn;
import com.peter.schoolmarket.network.ReqExecutor;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by PetterChen on 2017/5/7.
 */

public class DrawerTradeModel implements IDrawerTradeModel {

    @Override
    public void drawerTradeDataReq(final IDrawerTradeListener listener, int typeId, int page) {
        if (AppConf.useMock){
            listener.onComplete(new TradeMock().getTrades());
            return;
        }
        //请求

        final Result<List<Trade>> result = new Result<List<Trade>>().result(NetReturn.SERVER_ERROR);
        ReqExecutor
                .INSTANCE()
                .tradeReq()
                .getDrawerTrades(typeId, AppConf.size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<List<Trade>>>() {
                    @Override
                    public void onCompleted() {
                        listener.onComplete(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onComplete(result);
                    }
                    @Override
                    public void onNext(Result<List<Trade>> listResult) {
                        result.setCode(listResult.getCode());
                        result.setMsg(listResult.getMsg());
                        result.setData(listResult.getData());
                    }
                });
    }
}
