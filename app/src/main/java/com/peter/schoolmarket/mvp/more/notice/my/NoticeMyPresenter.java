package com.peter.schoolmarket.mvp.more.notice.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;
import com.peter.schoolmarket.adapter.recycler.RecyclerViewHolder;
import com.peter.schoolmarket.application.AppConf;
import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Notice;
import com.peter.schoolmarket.data.pojo.User;
import com.peter.schoolmarket.data.storage.LoginInfoExecutor;
import com.peter.schoolmarket.mvp.more.notice.detail.NoticeDetailActivity;
import com.peter.schoolmarket.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by PetterChen on 2017/5/22.
 */

class NoticeMyPresenter implements INoticeMyPresenter, INoticeMyListener {

    private INoticeMyModel model;
    private Context context;
    private INoticeMyView view;
    private int page = 1;
    private int myId;
    private boolean isLoadNextPage = false;
    private RecyclerCommonAdapter<?> adapter;
    private List<Notice> data = new ArrayList<>();
    private Realm realm;

    NoticeMyPresenter(Context context, INoticeMyView view) {
        model = new NoticeMyModel();
        this.context = context;
        this.view = view;
    }

    @Override
    public void initMain(Realm realm) {
        this.realm = realm;
        initList(data);
        User user = LoginInfoExecutor.getUser(context);
        myId = user.getId();
        view.showProgress();
        isLoadNextPage = false;
        page = 1;
        model.myNoticeReq(this, myId, page);
    }

    private void initList(List<Notice> data) {
        adapter = new RecyclerCommonAdapter<Notice>(context, data, R.layout.notice_my_item) {
            @Override
            public void convert(RecyclerViewHolder holder, final Notice item) {
                User user = realm.where(User.class).equalTo("id",item.getAuthorId()).findFirst();
                if (user != null) {
                    holder.setText(R.id.notice_my_item_name,user.getUsername());
                }
                holder.setText(R.id.notice_my_item_title,item.getTitle());
                holder.setText(R.id.notice_my_item_content,item.getContent());
                String tem = TimeUtils.getDate(item.getCreateTime());
                holder.setText(R.id.notice_my_item_time,tem);
                holder.setOnClickListener(R.id.notice_my_item_detail, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转到notice详情页面
                        //Toast.makeText(context, "jump to notice detail", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(context, NoticeDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("notice", item);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
                holder.setOnClickListener(R.id.notice_my_item_cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //商品下架
                        new MaterialDialog.Builder(context)
                                .title("公告下架？")
                                .content("公告下架后无法恢复，建议再考虑考虑！")
                                .positiveText("下架")
                                .negativeText("再想想")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        view.showProgress();
                                        model.deleteNoticeReq(NoticeMyPresenter.this, item.getId());
                                    }
                                })
                                .show();
                    }
                });
            }
        };
        view.loadDataSuccess(adapter);
    }

    @Override
    public void refresh() {
        isLoadNextPage = false;
        page = 1;
        model.myNoticeReq(this, myId, page);
    }

    @Override
    public void loadNextPage() {
        isLoadNextPage = true;
        view.showProgress();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (data.size() == page * AppConf.size) {
                    page++;
                }
                model.myNoticeReq(NoticeMyPresenter.this, myId, page);
            }
        }, 500);
    }

    @Override
    public void onNoticeReqComplete(Result<List<Notice>> result) {
        view.hideRefresh();
        view.hideProgress();
        switch (result.getCode()) {
            case 100://操作成功
                //loginRegisterView.loginSuccess();
                if (isLoadNextPage && result.getData().size() <= data.size()) {
                    Toast.makeText(context, "没有更多内容啦", Toast.LENGTH_SHORT).show();

                }
                data.clear();
                data.addAll(result.getData());
                adapter.notifyDataSetChanged();
                //view.onSuccess(result.getMsg());
                break;
            case 99://网络异常或者系统错误
                //loginRegisterView.loginFailed(result.getMsg());
                view.onFail(result.getMsg());
                break;
            default:
                break;
        }
    }

    @Override
    public void onDeleteReqComplete(Result<String> result) {
        view.hideProgress();
        switch (result.getCode()) {
            case 100://操作成功
                //loginRegisterView.loginSuccess();
                view.onSuccess(result.getData());
                view.showRefresh();
                isLoadNextPage = false;
                page = 1;
                model.myNoticeReq(this, myId, page);
                break;
            case 99://网络异常或者系统错误
                //loginRegisterView.loginFailed(result.getMsg());
                view.onFail(result.getMsg());
                break;
            default:
                break;
        }
    }
}
