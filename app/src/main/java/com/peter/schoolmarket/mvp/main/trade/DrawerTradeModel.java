package com.peter.schoolmarket.mvp.main.trade;

import com.peter.schoolmarket.application.AppConf;
import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Trade;
import com.peter.schoolmarket.data.pojo.User;
import com.peter.schoolmarket.data.storage.LoginInfoExecutor;
import com.peter.schoolmarket.mock.TradeMock;
import com.peter.schoolmarket.network.NetReturn;
import com.peter.schoolmarket.network.ReqExecutor;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by PetterChen on 2017/5/7.
 */

public class DrawerTradeModel implements IDrawerTradeModel {

    @Override
    public void drawerTradeDataReq(final IDrawerTradeListener listener, int typeId, int page, int myId) {
        /*if (AppConf.useMock){
            listener.onComplete(new TradeMock().getTrades());
            return;
        }*/
        final Result<List<Trade>> result = new Result<List<Trade>>().result(NetReturn.SERVER_ERROR);
        ReqExecutor
                .INSTANCE()
                .tradeReq()
                .getDrawerTrades(typeId, page, AppConf.size, myId)
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

    @Override
    public void confirmReceiveTrade(final IDrawerTradeListener listener, int myId, int tradeId) {
        final Result<String> result = new Result<String>().result(NetReturn.SERVER_ERROR);
        ReqExecutor
                .INSTANCE()
                .tradeReq()
                .confirmTrade(myId, tradeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<String>>() {
                    @Override
                    public void onCompleted() {
                        listener.confirmComplete(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.confirmComplete(result);
                    }
                    @Override
                    public void onNext(Result<String> listResult) {
                        result.setCode(listResult.getCode());
                        result.setMsg(listResult.getMsg());
                        result.setData(listResult.getData());
                    }
                });
    }

    @Override
    public void confirmReceiveMoney(final IDrawerTradeListener listener, int myId, int tradeId) {
        final Result<String> result = new Result<String>().result(NetReturn.SERVER_ERROR);
        ReqExecutor
                .INSTANCE()
                .tradeReq()
                .confirmMoney(myId, tradeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<String>>() {
                    @Override
                    public void onCompleted() {
                        listener.confirmComplete(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.confirmComplete(result);
                    }
                    @Override
                    public void onNext(Result<String> listResult) {
                        result.setCode(listResult.getCode());
                        result.setMsg(listResult.getMsg());
                        result.setData(listResult.getData());
                    }
                });
    }

    @Override
    public void cancelOrder(final IDrawerTradeListener listener, int tradeId) {
        final Result<String> result = new Result<String>().result(NetReturn.SERVER_ERROR);
        ReqExecutor
                .INSTANCE()
                .tradeReq()
                .cancelOrder(tradeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<String>>() {
                    @Override
                    public void onCompleted() {
                        listener.confirmComplete(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.confirmComplete(result);
                    }
                    @Override
                    public void onNext(Result<String> listResult) {
                        result.setCode(listResult.getCode());
                        result.setMsg(listResult.getMsg());
                        result.setData(listResult.getData());
                    }
                });
    }

    @Override
    public void cancelTradeReq(final IDrawerTradeListener listener, final int tradeId) {
        final Result<String> result = new Result<String>().result(NetReturn.SERVER_ERROR);
        ReqExecutor
                .INSTANCE()
                .tradeReq()
                .cancelTrade(tradeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<String>>() {
                    @Override
                    public void onCompleted() {
                        listener.cancelTradeComplete(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.cancelTradeComplete(result);
                    }

                    @Override
                    public void onNext(Result<String> result1) {
                        result.setCode(result1.getCode());
                        result.setMsg(result1.getMsg());
                        result.setData(result1.getData());
                    }
                });
    }
}
