package com.peter.schoolmarket.mvp.find;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.peter.schoolmarket.R;
import com.peter.schoolmarket.adapter.recycler.RecyclerCommonAdapter;
import com.peter.schoolmarket.adapter.recycler.RecyclerViewHolder;
import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Trade;
import com.peter.schoolmarket.util.ResultInterceptor;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by PetterChen on 2017/4/28.
 */

public class FindPresenter implements IFindPresenter, IGainListener {

    private IFindModel model;
    private IFindView view;
    private Context context;

    public FindPresenter(Context context,IFindView view) {
        this.context = context;
        this.view = view;
        this.model=new FindModel();
    }

    @Override
    public void initView(Realm realm) {
        RealmQuery<Trade> query =  realm.where(Trade.class);
        List<Trade> data =  query.findAll();
        if (data.size()>0){
            initList(data);
        }else {
            Toast.makeText(context, "no goods", Toast.LENGTH_LONG);
            //String schoolName= UserIntermediate.instance.getUser(context).getSchool();
            model.tradesDataReq(this, 0, realm);
        }
    }

    @Override
    public void refreshView(Realm realm) {
        //进行网络请求，查看是否更新数据
        model.tradesDataReq(this, 0, realm);
    }

    public void initList(final List<Trade> trades) {
        RecyclerCommonAdapter<?> adapter=new RecyclerCommonAdapter<Trade>(context,trades, R.layout.find_item) {
            @Override
            public void convert(RecyclerViewHolder holder, Trade item) {
                holder.setText(R.id.deal_title,item.getTitle());
                holder.setFrescoImg(R.id.deal_img, Uri.parse(item.getImgUrls()));
                holder.setFrescoImg(R.id.author_img,Uri.parse(item.getAuthor().getAvatarUrl()));
                holder.setText(R.id.author_name,item.getAuthor().getUsername());
                holder.setText(R.id.deal_price,"￥ "+item.getPrice());
            }
        };
        view.loadDataSuccess(adapter);
        adapter.setClickListener(new RecyclerCommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Trade item=trades.get(position);
                //跳转到商品详情页面
                Toast.makeText(context, "jump", Toast.LENGTH_LONG);
                /*Intent intent=new Intent(context,TradeDetailActivity.class);
                intent.putExtra("tradeId",item.getId());
                intent.putExtra("userId",item.getAuthorId());
                context.startActivity(intent);*/
            }
        });
    }

    @Override
    public void onReqComplete(Result<List<Trade>> result, Realm realmDefault) {
        view.hideRefresh();
        if (!ResultInterceptor.instance.resultDataHandler(result)){
            return;
        }
        final List<Trade> tradeList=new ArrayList<>();
        for (Trade trade:result.getData()){
            //添加trade的realm
            Trade newTrade=new Trade();
            newTrade.setId(trade.getId());
            newTrade.setTitle(trade.getTitle());
            newTrade.setPrice(trade.getPrice());
            /*newTrade.setImg(AppConf.BASE_URL+trade.getImgUrls().get(0));
            newTrade.setAuthorId(trade.getAuthor().getId());
            newTrade.setAuthorImg(AppConf.BASE_URL+trade.getAuthor().getAvatarUrl());
            newTrade.setAuthorName(trade.getAuthor().getUsername());*/
            tradeList.add(newTrade);
        }
        initList(tradeList);
        final RealmResults<Trade> results = realmDefault.where(Trade.class).findAll();
        realmDefault.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
        realmDefault.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(tradeList);
            }
        });
    }
}
