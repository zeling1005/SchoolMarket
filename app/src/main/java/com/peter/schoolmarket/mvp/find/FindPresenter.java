package com.peter.schoolmarket.mvp.find;

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
import com.peter.schoolmarket.data.pojo.User;
import com.peter.schoolmarket.mvp.trade.detail.TradeDetailActivity;
import com.peter.schoolmarket.network.RetrofitConf;
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
    private int page = 1;
    private boolean isLoadNextPage = false;
    private Realm realm;
    //private int total = 0;
    private List<Trade> data = new ArrayList<>();
    private RecyclerCommonAdapter<?> adapter;

    public FindPresenter(Context context,IFindView view) {
        this.context = context;
        this.view = view;
        this.model=new FindModel();
    }

    @Override
    public void initView(Realm realm) {
        initList(data);
        view.setSearchFlag(false);

        this.realm = realm;
        RealmQuery<Trade> query =  realm.where(Trade.class);
        RealmResults<Trade> results = query.findAll();
        List<Trade> list = realm.copyFromRealm(results);
        //data.addAll(realm.copyFromRealm(results));

        if (data.size()>0){
            //initList(data);
            if (data.size() > 8) {
                for (int i = 0; i < AppConf.size; i++) {
                    data.add(list.get(i));
                }
            } else {
                data.addAll(list);
            }
            adapter.notifyDataSetChanged();
            //total = data.size();
        }else {
            //这里可以添加一个加载窗口
            view.showProgress();
            page = 1;
            isLoadNextPage = false;
            model.tradesDataReq(this, page);
        }
    }

    @Override
    public void refreshView() {
        view.setSearchFlag(false);
        //进行网络请求，查看是否更新数据
        page = 1;
        isLoadNextPage = false;
        model.tradesDataReq(this, page);
    }

    private void initList(final List<Trade> trades) {
        adapter=new RecyclerCommonAdapter<Trade>(context,trades, R.layout.find_item) {
            @Override
            public void convert(RecyclerViewHolder holder, Trade item) {
                //Dog dog = mRealm.where(Dog.class).equalTo("id", id).findFirst();
                User user = realm.where(User.class).equalTo("id",item.getAuthorId()).findFirst();
                holder.setText(R.id.deal_title,item.getTitle());
                holder.setFrescoImg(R.id.deal_img, Uri.parse(AppConf.BASE_URL + RetrofitConf.base_img +
                        item.getImgUrl()));
                if (user != null) {
                    holder.setFrescoImg(R.id.author_img,Uri.parse(AppConf.BASE_URL + RetrofitConf.base_img +
                            user.getImgUrl()));
                    holder.setText(R.id.author_name,user.getUsername());
                }
                holder.setText(R.id.deal_price,"￥ "+item.getNowPrice());
            }
        };
        view.loadDataSuccess(adapter);
        adapter.setClickListener(new RecyclerCommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Trade item=trades.get(position);

                //跳转到商品详情页面
                //Toast.makeText(context, "jump", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(context, TradeDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("trade", item);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public void onReqComplete(Result<List<Trade>> result) {
        view.hideRefresh();
        view.hideProgress();
        if (!ResultInterceptor.instance.resultDataHandler(result)){//判断是否Result数据为空
            return;
        }
        if (isLoadNextPage && result.getData().size() <= data.size()) {
            Toast.makeText(context, "没有更多内容啦", Toast.LENGTH_SHORT).show();
        }
        data.clear();
        data.addAll(result.getData());
        adapter.notifyDataSetChanged();
        //final List<Trade> tradeList = result.getData();
        //total = data.size();
        //initList(tradeList);
        final RealmResults<Trade> results = realm.where(Trade.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {//清空数据
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
        //Async
        realm.executeTransaction(new Realm.Transaction() {//重新加载数据
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(data);
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
                model.tradesDataReq(FindPresenter.this, page);
            }
        }, 500);
    }

    @Override
    public void loadSearchPage(String query) {
        view.showProgress();
        page = 1;
        isLoadNextPage = false;
        model.searchDataReq(this, query);
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
                            refreshView();
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
