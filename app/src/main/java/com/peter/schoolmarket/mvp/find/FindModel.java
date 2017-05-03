package com.peter.schoolmarket.mvp.find;

import com.peter.schoolmarket.application.AppConf;
import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Trade;
import com.peter.schoolmarket.data.pojo.User;
import com.peter.schoolmarket.mock.TradeTagMock;
import com.peter.schoolmarket.mock.UserMock;
import com.peter.schoolmarket.network.NetReturn;
import com.peter.schoolmarket.network.ReqExecutor;

import java.util.List;

import io.realm.Realm;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by PetterChen on 2017/4/28.
 */

public class FindModel implements IFindModel {

    @Override
    public void tradesDataReq(final IGainListener listener, final int page, final Realm realm) {
        //添加一个测试代码，测试代码是否正确
        if (AppConf.useMock){
            listener.onReqComplete(new TradeTagMock().getTrades(), realm);
            return;
        }
        final Result<List<Trade>> result = new Result<List<Trade>>().result(NetReturn.SERVER_ERROR);
        ReqExecutor
                .INSTANCE()
                .tradeReq()
                .getFindTrades(page,AppConf.size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<List<Trade>>>() {
                    @Override
                    public void onCompleted() {
                        listener.onReqComplete(result,realm);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onReqComplete(result,realm);
                    }
                    @Override
                    public void onNext(Result<List<Trade>> listResult) {
                        result.setCode(listResult.getCode());
                        result.setMsg(listResult.getMsg());
                        result.setData(listResult.getData());
                    }
                });
    }

    /*@Override
    public void getUserInfo(final IGainListener listener, final String userId) {
        //添加一个测试代码，测试代码是否正确
        if (AppConf.useMock){
            listener.onReqUserInfoComplete(new UserMock().getUserInfo());
            return;
        }

        final Result<User> result = new Result<User>().result(NetReturn.SERVER_ERROR);
        ReqExecutor
                .INSTANCE()
                .userReq()
                .getUserInfo(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<User>>() {
                    @Override
                    public void onCompleted() {
                        listener.onReqUserInfoComplete(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onReqUserInfoComplete(result);
                    }
                    @Override
                    public void onNext(Result<User> listResult) {
                        result.setCode(listResult.getCode());
                        result.setMsg(listResult.getMsg());
                        result.setData(listResult.getData());
                    }
                });
    }*/
}
