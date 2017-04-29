package com.peter.schoolmarket.mvp.find;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.peter.schoolmarket.R;
import com.peter.schoolmarket.adapter.recycler.DividerItemDecoration;
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
    protected IFindPresenter presenter;

    protected RecyclerView recyclerView;

    SwipeRefreshLayout refreshLayout;

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

        DaggerFindFragmentComponent.builder()
                .findFragmentModule(new FindFragmentModule(getActivity(),this))
                .build()
                .inject(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        /*recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(),
                DividerItemDecoration.VERTICAL_LIST));//分割线*/

        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refreshView(realm);
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
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

}
