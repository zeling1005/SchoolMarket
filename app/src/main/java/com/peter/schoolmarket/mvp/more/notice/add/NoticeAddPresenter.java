package com.peter.schoolmarket.mvp.more.notice.add;

import android.content.Context;
import android.widget.EditText;

import com.google.gson.Gson;
import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Notice;
import com.peter.schoolmarket.data.pojo.User;
import com.peter.schoolmarket.data.storage.LoginInfoExecutor;
import com.peter.schoolmarket.network.NetReturn;
import com.peter.schoolmarket.network.RetrofitUtils;
import com.peter.schoolmarket.util.ResultInterceptor;

import okhttp3.RequestBody;

/**
 * Created by PetterChen on 2017/5/7.
 */

class NoticeAddPresenter implements INoticeAddPresenter, INoticeAddListener {
    private INoticeAddView view;
    private INoticeAddModel model;
    private Context context;

    NoticeAddPresenter(Context context, INoticeAddView view) {
        this.view = view;
        this.context = context;
        model = new NoticeAddModel();
    }

    @Override
    public void releaseNotice(EditText title, EditText content) {
        Notice notice = checkForm(title, content);
        if (!notice.isReleaseCheck()){
            return;
        }
        view.showProgress();
        String tradeJsonStr=new Gson().toJson(notice);
        RequestBody tradeJson=RetrofitUtils.createPartFromString(tradeJsonStr);
        model.addNoticeReq(tradeJson ,this);
    }

    @Override
    public void addNoticeResult(Result<String> result) {
        view.hideProgress();
        if (ResultInterceptor.instance.resultHandler(result)){
            view.noticeAddSuccess();
        }else {
            view.whenFail(NetReturn.SERVER_ERROR.msg());
        }
    }

    private Notice checkForm(EditText title, EditText content) {
        Notice notice = new Notice();
        notice.setReleaseCheck(false);

        String titleData = title.getText().toString().trim();
        if (titleData.isEmpty()) {
            view.whenFail("标题不可以为空");
            return notice;
        }
        notice.setTitle(titleData);

        String contentData = content.getText().toString().trim();
        if (contentData.isEmpty()) {
            view.whenFail("内容不可以为空");
            return notice;
        }
        notice.setContent(contentData);

        notice.setCreateTime(System.currentTimeMillis());//设置商品发布时间

        User authorOld= LoginInfoExecutor.getUser(context);
        if (authorOld==null || authorOld.getId()==null){
            return notice;
        }
        notice.setAuthorId(authorOld.getId());
        notice.setAuthorName(authorOld.getUsername());
        notice.setAuthorImg(authorOld.getAvatarUrl());
        notice.setAuthorPhone(authorOld.getPhone());

        notice.setReleaseCheck(true);
        return notice;
    }
}
