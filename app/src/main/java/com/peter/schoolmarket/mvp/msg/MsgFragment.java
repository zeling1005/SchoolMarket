package com.peter.schoolmarket.mvp.msg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;
import com.peter.schoolmarket.adapter.recycler.RecyclerViewHolder;
import com.peter.schoolmarket.data.pojo.Msg;
import com.peter.schoolmarket.mvp.base.BaseFragment;
import com.peter.schoolmarket.util.TimeUtils;

import java.util.List;

import io.realm.MsgRealmProxy;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by PetterChen on 2017/5/6.
 */

public class MsgFragment extends BaseFragment implements IMsgView {
    public static String MY_BROADCAST_ACTION = "com.peter.mvp.msg.BROADCAST";
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private IMsgPresenter presenter;
    private MaterialDialog progress;
    private Realm realm;

    private MsgChangeReceiver receiver;
    private IntentFilter intentFilter;

    @Override
    protected int getLayoutResId() {
        return R.layout.message_fragment;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        initVariable(view);
        manageVariable();
        presenter.init();
    }

    private void initVariable(View view) {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.test_msg_refresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.test_msg_list);
        realm = Realm.getDefaultInstance();
        presenter = new MsgPresenter(this, getActivity(), realm);
        progress = new MaterialDialog.Builder(getActivity())
                .content("正在加载...")
                .progress(true, 0)
                .progressIndeterminateStyle(false)//是否水平放置
                .title("请稍等")
                .build();

        intentFilter = new IntentFilter();
        intentFilter.addAction(MsgFragment.MY_BROADCAST_ACTION);
        receiver = new MsgChangeReceiver();

        Intent startIntent = new Intent(getActivity(), MsgService.class);
        getActivity().startService(startIntent); // 启动服务

        getActivity().registerReceiver(receiver, intentFilter);
    }

    private void manageVariable() {
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void recyclerViewChange() {
        //Log.d("getMsgsmsgs", "recyclerViewChange()");
        presenter.updateList();
    }

    @Override
    public void loadDataSuccess(RecyclerCommonAdapter<?> adapter) {
        //Log.d("getMsgsmsgs", "loadDataSuccess()");
        recyclerView.setAdapter(adapter);
    }

    class MsgChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Toast.makeText(context, "msg changes", Toast.LENGTH_SHORT).show();
            //Log.d("getMsgsmsgs", "Presenter执行广播");
            recyclerViewChange();
        }
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
        getActivity().unregisterReceiver(receiver);

        Intent stopIntent = new Intent(getActivity(), MsgService.class);
        getActivity().stopService(stopIntent); // 停止服务

        realm.close();
    }

}
