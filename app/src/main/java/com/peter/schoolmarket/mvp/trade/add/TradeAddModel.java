package com.peter.schoolmarket.mvp.trade.add;

import com.peter.schoolmarket.application.AppConf;
import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.mock.TradeMock;
import com.peter.schoolmarket.network.NetReturn;
import com.peter.schoolmarket.network.ReqExecutor;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by PetterChen on 2017/4/30.
 */

public class TradeAddModel implements ITradeAddModel {
    @Override
    public void addTradeReq(final RequestBody tradeBody, final RequestBody photoBody, final ITradeAddListener listener) {
        /*if (AppConf.useMock){
            listener.addTradeResult(new TradeMock().getReleaseTradeResult());
            return;
        }*/

        final Result<String> result= new Result<String>().result(NetReturn.SERVER_ERROR);
        ReqExecutor
                .INSTANCE()
                .tradeReq()
                .addTrade(tradeBody,photoBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<String>>() {
                    @Override
                    public void onCompleted() {
                        listener.addTradeResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        result.setCode(NetReturn.SERVER_ERROR.code());
                        listener.addTradeResult(result);
                    }

                    @Override
                    public void onNext(Result<String> resultData) {
                        result.setCode(resultData.getCode());
                        result.setMsg(resultData.getMsg());
                    }
                });
    }
}
