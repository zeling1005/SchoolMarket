package com.peter.schoolmarket.mvp.trade.detail;

import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.User;
import com.peter.schoolmarket.network.NetReturn;
import com.peter.schoolmarket.network.ReqExecutor;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by PetterChen on 2017/4/30.
 */

public class TradeDetailModel implements ITradeDetailModel {
    @Override
    public void placeOrderReq(final ITradeDetailListener listener, User user, String tradeId) {
        /*if (AppConf.useMock){
            return;
        }*/

        final Result<String> result =new Result<String>().result(NetReturn.SERVER_ERROR);
        /*ReqExecutor
                .INSTANCE()
                .tradeReq()
                .createDeal(user.getId(),user.getUsername(),user.getAvatarUrl(),tradeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<String>>() {
                    @Override
                    public void onCompleted() {
                        listener.placeOrderComplete(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.placeOrderComplete(result);
                    }
                    @Override
                    public void onNext(Result<String> tradeResult) {
                        result.setCode(tradeResult.getCode());
                        result.setMsg(tradeResult.getMsg());
                    }
                });*/
    }
}
