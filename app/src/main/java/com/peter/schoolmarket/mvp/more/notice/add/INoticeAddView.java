package com.peter.schoolmarket.mvp.more.notice.add;

/**
 * Created by PetterChen on 2017/5/7.
 */

public interface INoticeAddView {
    void noticeAddSuccess();//发布商品成功

    void whenFail(String errorMsg);//发生异常

    void showProgress();//显示进度条

    void hideProgress();//隐藏进度条
}
