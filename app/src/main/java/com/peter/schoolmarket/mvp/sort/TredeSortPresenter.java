package com.peter.schoolmarket.mvp.sort;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.peter.schoolmarket.R;
import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;
import com.peter.schoolmarket.adapter.recycler.RecyclerViewHolder;
import com.peter.schoolmarket.data.pojo.TradeTag;
import com.peter.schoolmarket.mvp.sort.trades.TradeTagDetailActivity;
import com.peter.schoolmarket.util.TradeTagUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PetterChen on 2017/4/29.
 */

public class TredeSortPresenter implements ITradeSortPresenter {
    private static int TAG_NUM = 5;
    private List<TradeTag> tagList;

    private ITradeSortView view;
    private Context context;

    public TredeSortPresenter(Context context,ITradeSortView view) {
        this.context = context;
        this.view = view;
    }


    @Override
    public void initView() {
        initData();
        initList(tagList);
    }

    private void initData() {
        tagList = TradeTagUtils.getTradeTag();
    }

    public void initList(final List<TradeTag> tags) {
        RecyclerCommonAdapter<?> adapter=new RecyclerCommonAdapter<TradeTag>(context,tags, R.layout.sort_item) {
            @Override
            public void convert(RecyclerViewHolder holder, TradeTag item) {
                holder.setText(R.id.grid_tv,item.getName());
                holder.setFrescoImg(R.id.grid_iv, Uri.parse("res://drawable/" + item.getRId()));
            }
        };
        view.loadTradeTagData(adapter);
        adapter.setClickListener(new RecyclerCommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TradeTag item=tags.get(position);
                String tagName = item.getName();
                //跳转到分类商品详情页面
                //Toast.makeText(context, "jump to " + tagName, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,TradeTagDetailActivity.class);
                intent.putExtra("tagName", tagName);
                context.startActivity(intent);
            }
        });
    }
}
