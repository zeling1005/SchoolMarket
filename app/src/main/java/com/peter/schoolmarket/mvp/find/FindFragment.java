package com.peter.schoolmarket.mvp.find;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;
import com.peter.schoolmarket.di.components.DaggerFindFragmentComponent;
import com.peter.schoolmarket.di.modules.FindFragmentModule;
import com.peter.schoolmarket.mvp.base.BaseFragment;

import javax.inject.Inject;

import io.realm.Realm;

/**
 * Created by PetterChen on 2017/4/28.
 */

public class FindFragment extends BaseFragment implements IFindView {

    @Inject
    IFindPresenter presenter;
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    MaterialDialog progress;
    private Realm realm;
    private boolean searchFlag = false;


    @Override
    protected int getLayoutResId() {
        return R.layout.find_fragment;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {

        recyclerView = (RecyclerView) view.findViewById(R.id.find_deal_list);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.find_trades_refresh);

        realm=Realm.getDefaultInstance();

        progress = new MaterialDialog.Builder(view.getContext())
                .content("加载中...")
                .progress(true, 0)
                .progressIndeterminateStyle(false)//是否水平放置
                .title("请稍等")
                .build();

        DaggerFindFragmentComponent.builder()
                .findFragmentModule(new FindFragmentModule(getActivity(),this))
                .build()
                .inject(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /*@Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                *//*if (!recyclerView.canScrollVertically(1)) {
                    presenter.loadNextPage();
                }*//*
                if (recyclerView.computeVerticalScrollOffset() > 0 &&
                        recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                                >= recyclerView.computeVerticalScrollRange()) {
                    presenter.loadNextPage();
                }
            }*/

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

        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refreshView();
            }
        });
        presenter.initView(realm);
    }

    public void setSarchText(String text) {
        //Toast.makeText(getActivity(), "find:" + text, Toast.LENGTH_SHORT).show();
        searchFlag = true;
        presenter.loadSearchPage(text);
    }

    @Override
    public void loadDataSuccess(RecyclerCommonAdapter<?> adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void hideRefresh() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }

    }

    @Override
    public void showProgress() {
        if (!progress.isShowing()) {
            progress.show();
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
