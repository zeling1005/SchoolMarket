package com.peter.schoolmarket.mvp.more.notice.add;

import com.peter.schoolmarket.mvp.trade.add.ITradeAddListener;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by PetterChen on 2017/5/7.
 */

public interface INoticeAddModel {
    void addNoticeReq(RequestBody noticeJson, INoticeAddListener listener);
}
