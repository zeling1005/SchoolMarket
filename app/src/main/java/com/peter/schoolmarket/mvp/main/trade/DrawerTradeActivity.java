package com.peter.schoolmarket.mvp.main.trade;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.adapter.recycler.DividerItemNormalDecoration;
import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;
import com.peter.schoolmarket.mvp.base.BaseActivity;
import com.peter.schoolmarket.mvp.sort.trades.TradeTagDetailActivity;

/**
 * Created by PetterChen on 2017/5/7.
 */

public class DrawerTradeActivity extends BaseActivity implements IDrawerTradeView {
    SimpleDraweeView back;
    TextView title;
    SwipeRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    MaterialDialog progress;
    IDrawerTradePresenter presenter;
    String titleText;
    int typeId = -1;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.drawer_trade_activity);
        initVariate();
        manageVariate();
        presenter.init(typeId);
    }

    private void initVariate() {
        back = (SimpleDraweeView) findViewById(R.id.drawer_trade_toolbar_back);
        title = (TextView) findViewById(R.id.drawer_trade_toolbar_title);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.drawer_trade_refresh);
        recyclerView = (RecyclerView) findViewById(R.id.drawer_trade_list);
        progress = new MaterialDialog.Builder(this)
                .content("正在加载...")
                .progress(true, 0)
                .progressIndeterminateStyle(false)//是否水平进度条
                .title("请稍等")
                .build();
        presenter = new DrawerTradePresenter(this, this);
        titleText = getIntent().getStringExtra("title");
        typeId = getTypeId(titleText);
    }


    private void manageVariate() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerTradeActivity.this.finish();
            }
        });
        title.setText(titleText);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(DrawerTradeActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemNormalDecoration(this, DividerItemNormalDecoration.VERTICAL_LIST));
    }

    private int getTypeId(String text) {
        if (text.equals("待确认")) {
            return 0;
        }
        if (text.equals("已买")) {
            return 1;
        }
        if (text.equals("正在卖")) {
            return 2;
        }
        if (text.equals("已卖")) {
            return 3;
        }
        return -1;
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
    public void confirmSuccess(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
