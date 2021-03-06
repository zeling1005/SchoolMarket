package com.peter.schoolmarket.mvp.more;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.adapter.recycler.DividerItemNormalDecoration;
import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;
import com.peter.schoolmarket.mvp.base.BaseFragment;
import com.peter.schoolmarket.mvp.more.notice.add.NoticeAddActivity;

import io.realm.Realm;

/**
 * Created by PetterChen on 2017/5/5.
 */

public class MoreFragment extends BaseFragment implements IMoreView {
    private MorePresenter presenter;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private FloatingActionButton noticePlus;
    private MaterialDialog progress;
    private Realm realm;
    private boolean searchFlag = false;

    @Override
    protected int getLayoutResId() {
        return R.layout.more_fragment;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        initVariate(view);
        manageVariate();
        presenter.init(realm);
    }

    private void initVariate(View view) {
        presenter = new MorePresenter(getActivity(), this);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.more_refresh);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        recyclerView = (RecyclerView) view.findViewById(R.id.more_list);
        noticePlus = (FloatingActionButton) view.findViewById(R.id.more_plus);
        progress = new MaterialDialog.Builder(getActivity())
                .content("正在加载...")
                .progress(true, 0)
                .progressIndeterminateStyle(false)//是否水平放置
                .title("请稍等")
                .build();
        realm=Realm.getDefaultInstance();
    }

    private void manageVariate() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh(realm);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemNormalDecoration(getActivity(),
                DividerItemNormalDecoration.VERTICAL_LIST));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                    if (recyclerView.computeVerticalScrollOffset() > 0 &&
                            recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                                    >= recyclerView.computeVerticalScrollRange()) {
                        if (!searchFlag) {
                            presenter.loadNextPage();
                        } else {
                            onSuccess("再怎么上拉也是没有数据啦");
                        }
                    }
                }
            }
        });
        //recyclerView.dispatchTouchEvent(new MotionEvent())
        noticePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加notice事件
                //Toast.makeText(MoreFragment.this.getActivity(), "notice plus", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), NoticeAddActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    public void setSarchText(String text) {
        //Toast.makeText(getActivity(), "more:" + text, Toast.LENGTH_SHORT).show();
        searchFlag = true;
        presenter.loadSearchPage(text);
    }

    @Override
    public void loadDataSuccess(RecyclerCommonAdapter<?> adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showProgress() {
        if (!progress.isShowing()) {
            progress.show();
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
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void onSuccess(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFail(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
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
    public void setSearchFlag(boolean searchFlag) {
        this.searchFlag = searchFlag;
    }
}
