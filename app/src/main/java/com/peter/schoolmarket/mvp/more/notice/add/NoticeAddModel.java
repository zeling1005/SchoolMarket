package com.peter.schoolmarket.mvp.more.notice.add;

import com.peter.schoolmarket.application.AppConf;
import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.mock.NoticeMock;
import com.peter.schoolmarket.mvp.trade.add.ITradeAddListener;
import com.peter.schoolmarket.network.NetReturn;
import com.peter.schoolmarket.network.ReqExecutor;

import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by PetterChen on 2017/5/7.
 */

public class NoticeAddModel implements INoticeAddModel {
    @Override
    public void addNoticeReq(final RequestBody noticeJson, final INoticeAddListener listener) {
        /*if (AppConf.useMock){
            listener.addNoticeResult(new NoticeMock().getReleaseNoticeResult());
            return;
        }*/

        final Result<String> result= new Result<String>().result(NetReturn.SERVER_ERROR);
        ReqExecutor
                .INSTANCE()
                .noticeReq()
                .addNotice(noticeJson)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<String>>() {
                    @Override
                    public void onCompleted() {
                        listener.addNoticeResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.addNoticeResult(result);
                    }

                    @Override
                    public void onNext(Result<String> resultData) {
                        result.setCode(resultData.getCode());
                        result.setMsg(resultData.getMsg());
                    }
                });
    }
}
