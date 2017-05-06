package com.peter.schoolmarket.mvp.msg;

import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.AndroidCharacter;
import android.util.AndroidException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;
import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Msg;
import com.peter.schoolmarket.mvp.base.BaseFragment;
import com.peter.schoolmarket.mvp.main.MainActivity;
import com.peter.schoolmarket.util.ResultInterceptor;

import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by PetterChen on 2017/5/6.
 */

public class MsgFragment extends BaseFragment implements IMsgView {
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private IMsgPresenter presenter;
    private MaterialDialog progress;
    private Realm realm;

    @Override
    protected int getLayoutResId() {
        return R.layout.message_fragment;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        initVariable(view);
        manageVariable();
        presenter.init(realm);
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
    public void presenterInitSuccess(RecyclerCommonAdapter<?> adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void initVariable(View view) {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.msg_refresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.msg_list);
        presenter = new MsgPresenter(this, getActivity());
        progress = new MaterialDialog.Builder(getActivity())
                .content("正在加载...")
                .progress(true, 0)
                .progressIndeterminateStyle(false)//是否水平放置
                .title("请稍等")
                .build();
        realm = Realm.getDefaultInstance();
    }

    private void manageVariable() {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh(realm);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

}
