package com.peter.schoolmarket.mvp.sort.trades;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.peter.schoolmarket.R;
import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;
import com.peter.schoolmarket.adapter.recycler.RecyclerViewHolder;
import com.peter.schoolmarket.application.AppConf;
import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Trade;
import com.peter.schoolmarket.mvp.test.TestActivity;
import com.peter.schoolmarket.mvp.trade.detail.TradeDetailActivity;
import com.peter.schoolmarket.network.RetrofitConf;
import com.peter.schoolmarket.util.ResultInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PetterChen on 2017/4/29.
 */

class TradeTagDetailPresenter implements ITradeTagDetailPresenter, ITradeTagDetailListener {

    private Context context;
    private ITradeTagDetailView view;
    private ITradeTagDetailModel model;
    private String tagName;
    private int page = 1;
    private boolean isLoadNextPage = false;
    private RecyclerCommonAdapter<?> adapter;
    private List<Trade> data = new ArrayList<>();

    TradeTagDetailPresenter(Context context, ITradeTagDetailView view) {
        this.context = context;
        this.view = view;
        this.model=new TradeTagDetailModel();
    }

    @Override
    public void init(String tagName) {
        view.setSearchFlag(false);
        initList(data);
        this.tagName = tagName;
        isLoadNextPage = false;
        page = 1;
        getTradeListByTag();
    }

    @Override
    public void refresh() {
        view.setSearchFlag(false);
        page = 1;
        isLoadNextPage = false;
        model.tradesDataReq(this, tagName, page);
    }

    private void getTradeListByTag() {
        view.showProgress();
        model.tradesDataReq(this, tagName, page);
    }

    @Override
    public void onComplete(final Result<List<Trade>> result) {
        view.hideProgress();
        view.hideRefresh();
        if (!ResultInterceptor.instance.resultDataHandler(result)){
            return;
        }
        if (isLoadNextPage && result.getData().size() <= data.size()) {
            Toast.makeText(context, "没有更多内容啦", Toast.LENGTH_SHORT).show();

        }
        data.clear();
        data.addAll(result.getData());
        adapter.notifyDataSetChanged();
    }

    private void initList(final List<Trade> data) {
        adapter =new RecyclerCommonAdapter<Trade>(context,data, R.layout.trade_tag_detail_item) {
            @Override
            public void convert(RecyclerViewHolder viewHolder, Trade item) {
                viewHolder.setFrescoImg(R.id.trade_tag_detail_img, Uri.parse(AppConf.BASE_URL +
                        RetrofitConf.base_img + item.getImgUrl()));
                viewHolder.setText(R.id.trade_tag_detail_name, item.getTitle());
                viewHolder.setText(R.id.trade_tag_detail_now_price, "友情价 ￥" + item.getNowPrice());
                viewHolder.setText(R.id.trade_tag_detail_original_price, "原价 ￥" + item.getOriginalPrice());
            }
        };
        view.loadDataSuccess(adapter);
        adapter.setClickListener(new RecyclerCommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Trade item=data.get(position);

                //跳转到商品详情页面
                //Toast.makeText(context, "jumpTradeDetail", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, TradeDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("trade", item);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
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
                model.tradesDataReq(TradeTagDetailPresenter.this, tagName, page);
            }
        }, 500);
        /*if (data.size() < (page * AppConf.size)) {
            if (page != 1) {
                Toast.makeText(context, "没有更多内容啦", Toast.LENGTH_SHORT).show();
            }
        } else {
            view.showProgress();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    page++;
                    model.tradesDataReq(TradeTagDetailPresenter.this, tagName, page);
                }
            }, 500);
        }*/
    }

    @Override
    public void loadSearchPage(String query) {
        view.showProgress();
        page = 1;
        isLoadNextPage = false;
        model.searchDataReq(this, query, tagName);
    }

    @Override
    public void onSearchReqComplete(Result<List<Trade>> result) {
        view.hideProgress();
        switch (result.getCode()) {
            case 100 :
                if (result.getData() != null) {
                    data.clear();
                    data.addAll(result.getData());
                    adapter.notifyDataSetChanged();
                } else {
                    view.onSuccess("没有搜索到相关信息");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view.showRefresh();
                            refresh();
                        }
                    }, 500);
                }
                break;
            case 99 :
                view.onFail(result.getMsg());
                break;
            default:
                break;
        }
    }
}
