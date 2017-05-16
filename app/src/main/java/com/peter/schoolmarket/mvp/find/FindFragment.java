package com.peter.schoolmarket.mvp.find;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;
import com.peter.schoolmarket.di.components.DaggerFindFragmentComponent;
import com.peter.schoolmarket.di.modules.FindFragmentModule;
import com.peter.schoolmarket.mvp.base.BaseFragment;
import com.peter.schoolmarket.mvp.main.MainActivity;
import com.peter.schoolmarket.mvp.splash.SplashActivity;

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
                .content("正在加载...")
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

}
