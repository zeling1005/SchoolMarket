package com.peter.schoolmarket.mvp.more.notice.my;

/**
 * Created by PetterChen on 2017/5/22.
 */

public interface INoticeMyModel {
    void myNoticeReq(INoticeMyListener listener, int userId, int page);
    void deleteNoticeReq(INoticeMyListener listener, int noticeId);
}
