package com.peter.schoolmarket.mvp.sort.trades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.adapter.recycler.DividerItemGridDecoration;
import com.peter.schoolmarket.adapter.recycler.DividerItemNormalDecoration;
import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;
import com.peter.schoolmarket.mvp.base.BaseActivity;

/**
 * Created by PetterChen on 2017/4/29.
 */

public class TradeTagDetailActivity extends BaseActivity implements ITradeTagDetailView {

    protected RecyclerView recyclerView;
    TradeTagDetailPresenter presenter;
    private SimpleDraweeView back;
    SwipeRefreshLayout refreshLayout;
    MaterialDialog progress;
    TextView title;
    private SearchView mSearchView;
    private String tagName;
    private boolean searchFlag = false;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.trade_tag_detail_activity);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        tagName=bundle.getString("tagName");

        recyclerView = (RecyclerView) findViewById(R.id.trade_tag_detail_list);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.trade_tag_detail_refresh);
        presenter = new TradeTagDetailPresenter(this, this);
        back = (SimpleDraweeView) findViewById(R.id.trade_tag_back);
        title = (TextView) findViewById(R.id.trade_tag_title);
        mSearchView = (SearchView) findViewById(R.id.trade_tag_search);

        title.setText(tagName);

        progress = new MaterialDialog.Builder(this)
                .content("正在加载...")
                .progress(true, 0)
                .progressIndeterminateStyle(false)//是否水平进度条
                .title("请稍等")
                .build();

        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (TradeTagDetailActivity.this).finish();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(TradeTagDetailActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemNormalDecoration(this, DividerItemNormalDecoration.VERTICAL_LIST));
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
        setSearchView();
        presenter.init(tagName);
    }

    private void setSearchView() {
        mSearchView.setIconifiedByDefault(true);
        final int closeImgId = getResources().getIdentifier("search_close_btn", "id", getPackageName());
        ImageView closeImg = (ImageView) mSearchView.findViewById(closeImgId);
        if (closeImg != null) {
            closeImg.setImageResource(R.drawable.ic_search_cancel);
        }
        final int editViewId = getResources().getIdentifier("search_src_text", "id", getPackageName());
        SearchView.SearchAutoComplete mEdit = (SearchView.SearchAutoComplete) mSearchView.findViewById(editViewId);
        if (mEdit != null) {
            mEdit.setHintTextColor(getResources().getColor(R.color.text_color));
            mEdit.setTextColor(getResources().getColor(R.color.black));
            mEdit.setHint("Search...");
        }
        final int searchImgId = getResources().getIdentifier("search_button", "id", getPackageName());
        ImageView searchImg = (ImageView) mSearchView.findViewById(searchImgId);
        if (searchImg != null) {
            searchImg.setImageResource(R.drawable.ic_search);
        }
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(MainActivity.this, "shoushuo",Toast.LENGTH_SHORT).show();
                setSarchText(query);
                /*if (!mSearchView.isIconified()) {
                    mSearchView.setIconified(true);
                }*/
                mSearchView.onActionViewCollapsed();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setSarchText(String text) {
        //Toast.makeText(getActivity(), "more:" + text, Toast.LENGTH_SHORT).show();
        searchFlag = true;
        presenter.loadSearchPage(text);
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
    public void onSuccess(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFail(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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

    public void onBackPressed()
    {
        if (!mSearchView.isIconified()) {
            //Toast.makeText(MainActivity.this, "search shoushuo",Toast.LENGTH_SHORT).show();
            mSearchView.setIconified(true);
        } else {
            //Toast.makeText(MainActivity.this, "shoushuo",Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        }
    }
}
