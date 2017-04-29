package com.peter.schoolmarket.mvp.sort;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.peter.schoolmarket.R;
import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;
import com.peter.schoolmarket.adapter.recycler.RecyclerViewHolder;
import com.peter.schoolmarket.data.pojo.TradeTag;

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
        tagList = new ArrayList<>();
        for (int i = 0; i < TAG_NUM; i++) {
            TradeTag newTradeTag = new TradeTag();
            newTradeTag.setRId(R.drawable.sort_book);
            newTradeTag.setName("学习资料");
            tagList.add(newTradeTag);
        }
    }

    public void initList(final List<TradeTag> tags) {
        RecyclerCommonAdapter<?> adapter=new RecyclerCommonAdapter<TradeTag>(context,tags, R.layout.sort_fragment) {
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
                TredeSortPresenter.this.view.jumpTradeDetail(tagName);
                //跳转到分类商品详情页面
                Toast.makeText(context, "jump", Toast.LENGTH_LONG);
                /*Intent intent=new Intent(context,TradeDetailActivity.class);
                intent.putExtra("tradeId",item.getId());
                intent.putExtra("userId",item.getAuthorId());
                context.startActivity(intent);*/
            }
        });
    }
}
