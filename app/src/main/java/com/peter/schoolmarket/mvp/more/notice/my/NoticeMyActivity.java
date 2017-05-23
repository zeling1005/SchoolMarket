package com.peter.schoolmarket.mvp.more.notice.my;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.adapter.recycler.DividerItemNormalDecoration;
import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;
import com.peter.schoolmarket.mvp.base.BaseActivity;

import io.realm.Realm;

/**
 * Created by PetterChen on 2017/5/22.
 */

public class NoticeMyActivity extends BaseActivity implements INoticeMyView {
    SimpleDraweeView back;
    SwipeRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    MaterialDialog progress;
    INoticeMyPresenter presenter;
    Realm realm;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.notice_my_activity);
        initVariate();
        manageVariate();
        presenter.initMain(realm);
    }

    private void initVariate() {
        back = (SimpleDraweeView) findViewById(R.id.notice_my_toolbar_back);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.notice_my_refresh);
        recyclerView = (RecyclerView) findViewById(R.id.notice_my_list);
        progress = new MaterialDialog.Builder(this)
                .content("正在加载...")
                .progress(true, 0)
                .progressIndeterminateStyle(false)//是否水平进度条
                .title("请稍等")
                .build();
        realm = Realm.getDefaultInstance();
        presenter = new NoticeMyPresenter(this, this);
    }


    private void manageVariate() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoticeMyActivity.this.finish();
            }
        });
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(NoticeMyActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemNormalDecoration(this, DividerItemNormalDecoration.VERTICAL_LIST));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                    if (recyclerView.computeVerticalScrollOffset() > 0 &&
                            recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                                    >= recyclerView.computeVerticalScrollRange()) {
                        presenter.loadNextPage();
                    }
                }
            }
        });
    }

    @Override
    public void loadDataSuccess(RecyclerCommonAdapter<?> adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void loadDataFail(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        if (!progress.isShowing()) {
            progress.show();
        }
    }

    @Override
    public void showRefresh() {
        if (!refreshLayout.isRefreshing()) {
            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(true);
                }
            });
        }
    }

    @Override
    public void hideRefresh() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void hideProgress() {
        if (progress.isShowing()) {
            progress.dismiss();
        }
    }

    @Override
    public void onSuccess(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
