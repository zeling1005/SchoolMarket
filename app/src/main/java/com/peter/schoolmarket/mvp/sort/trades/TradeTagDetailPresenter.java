package com.peter.schoolmarket.mvp.sort.trades;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;
import com.peter.schoolmarket.adapter.recycler.RecyclerViewHolder;
import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Trade;
import com.peter.schoolmarket.util.ResultInterceptor;

import java.util.List;

/**
 * Created by PetterChen on 2017/4/29.
 */

public class TradeTagDetailPresenter implements ITradeTagDetailPresenter, ITradeTagDetailListener {

    private AppCompatActivity context;
    private ITradeTagDetailView view;
    private ITradeTagDetailModel model;

    public TradeTagDetailPresenter(AppCompatActivity context, ITradeTagDetailView view) {
        this.context = context;
        this.view = view;
        this.model=new TradeTagDetailModel();
    }

    @Override
    public void init(MaterialSearchView searchView) {
        searchView.setVoiceSearch(false);//关闭声音搜索
        searchView.setCursorDrawable(R.drawable.trade_tag_search_cursor);
        searchView.setSuggestions(context.getResources().getStringArray(R.array.query_suggestions));//建议候选词
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic进行搜索
                Snackbar.make(context.findViewById(R.id.toolbar_container), "Query: " + query, Snackbar.LENGTH_LONG)
                        .show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }

    @Override
    public void getTradeListByTag(String tagName) {
        model.tradesDataReq(this, tagName, 1);
    }

    @Override
    public void onComplete(final Result<List<Trade>> result) {
        if (!ResultInterceptor.instance.resultDataHandler(result)){
            return;
        }
        RecyclerCommonAdapter<?> adapter=new RecyclerCommonAdapter<Trade>(context,result.getData(), R.layout.trade_tag_detail_item) {
            @Override
            public void convert(RecyclerViewHolder viewHolder, Trade item) {
                viewHolder.setFrescoImg(R.id.trade_tag_detail_img, Uri.parse(item.getImgUrls()));
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
                Toast.makeText(context, "jump", Toast.LENGTH_LONG).show();
                /*Intent intent=new Intent(context,TradeDetailActivity.class);
                intent.putExtra("tradeId",item.getId());
                intent.putExtra("userId",item.getAuthorId());
                context.startActivity(intent);*/

            }
        });
    }
}
