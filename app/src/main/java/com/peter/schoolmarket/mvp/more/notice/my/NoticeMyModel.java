package com.peter.schoolmarket.mvp.more.notice.my;

import com.peter.schoolmarket.application.AppConf;
import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Notice;
import com.peter.schoolmarket.network.NetReturn;
import com.peter.schoolmarket.network.ReqExecutor;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by PetterChen on 2017/5/22.
 */

class NoticeMyModel implements INoticeMyModel {
    @Override
    public void myNoticeReq(final INoticeMyListener listener, int userId, int page) {
        final Result<List<Notice>> result = new Result<List<Notice>>().result(NetReturn.SERVER_ERROR);
        ReqExecutor
                .INSTANCE()
                .noticeReq()
                .getMyNotices(userId, page, AppConf.size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<List<Notice>>>() {
                    @Override
                    public void onCompleted() {
                        listener.onNoticeReqComplete(result);
                    }
                    @Override
                    public void onError(Throwable e) {
                        listener.onNoticeReqComplete(result);
                    }
                    @Override
                    public void onNext(Result<List<Notice>> listResult) {
                        result.setCode(listResult.getCode());
                        result.setMsg(listResult.getMsg());
                        result.setData(listResult.getData());
                    }
                });
    }

    @Override
    public void deleteNoticeReq(final INoticeMyListener listener, int noticeId) {
        final Result<String> result = new Result<String>().result(NetReturn.SERVER_ERROR);
        ReqExecutor
                .INSTANCE()
                .noticeReq()
                .deleteNotice(noticeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Result<String>>() {
                    @Override
                    public void onCompleted() {
                        listener.onDeleteReqComplete(result);
                    }
                    @Override
                    public void onError(Throwable e) {
                        listener.onDeleteReqComplete(result);
                    }
                    @Override
                    public void onNext(Result<String> listResult) {
                        result.setCode(listResult.getCode());
                        result.setMsg(listResult.getMsg());
                        result.setData(listResult.getData());
                    }
                });
    }
}
