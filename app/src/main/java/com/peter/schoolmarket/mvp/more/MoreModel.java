package com.peter.schoolmarket.mvp.more;

import com.peter.schoolmarket.application.AppConf;
import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Notice;
import com.peter.schoolmarket.mock.NoticeMock;
import com.peter.schoolmarket.network.NetReturn;
import com.peter.schoolmarket.network.ReqExecutor;

import java.util.List;

import io.realm.Realm;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by PetterChen on 2017/5/5.
 */

public class MoreModel implements IMoreModel {
    @Override
    public void noticeDataReq(final IMoreListener listener,final int page,final Realm realm) {
        //添加一个测试代码，测试代码是否正确
        /*if (AppConf.useMock){
            listener.onReqComplete(new NoticeMock().getNotices(), realm);
            return;
        }*/

        final Result<List<Notice>> result = new Result<List<Notice>>().result(NetReturn.SERVER_ERROR);
        ReqExecutor
                .INSTANCE()
                .noticeReq()
                .getNoticesData(page,AppConf.size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<List<Notice>>>() {
                    @Override
                    public void onCompleted() {
                        listener.onReqComplete(result,realm);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onReqComplete(result,realm);
                    }
                    @Override
                    public void onNext(Result<List<Notice>> listResult) {
                        result.setCode(listResult.getCode());
                        result.setMsg(listResult.getMsg());
                        result.setData(listResult.getData());
                    }
                });
    }
}
