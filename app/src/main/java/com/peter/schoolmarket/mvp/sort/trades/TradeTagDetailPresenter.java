package com.peter.schoolmarket.mvp.sort.trades;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import java.util.List;

/**
 * Created by PetterChen on 2017/4/29.
 */

class TradeTagDetailPresenter implements ITradeTagDetailPresenter, ITradeTagDetailListener {

    private Context context;
    private ITradeTagDetailView view;
    private ITradeTagDetailModel model;
    private String tagName;
    private static int page = 1;

    TradeTagDetailPresenter(Context context, ITradeTagDetailView view) {
        this.context = context;
        this.view = view;
        this.model=new TradeTagDetailModel();
    }

    @Override
    public void init(String tagName) {
        this.tagName = tagName;
        getTradeListByTag();
    }

    @Override
    public void refresh() {
        model.tradesDataReq(this, tagName, 1);
    }

    private void getTradeListByTag() {
        view.showProgress();
        model.tradesDataReq(this, tagName, 1);
    }

    @Override
    public void onComplete(final Result<List<Trade>> result) {
        view.hideProgress();
        view.hideRefresh();
        if (!ResultInterceptor.instance.resultDataHandler(result)){
            return;
        }

        RecyclerCommonAdapter<?> adapter=new RecyclerCommonAdapter<Trade>(context,result.getData(), R.layout.trade_tag_detail_item) {
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
                Trade item=result.getData().get(position);

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
}
