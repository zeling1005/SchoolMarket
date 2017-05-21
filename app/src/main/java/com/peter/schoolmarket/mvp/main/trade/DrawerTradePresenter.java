package com.peter.schoolmarket.mvp.main.trade;

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
import com.peter.schoolmarket.data.pojo.User;
import com.peter.schoolmarket.data.storage.LoginInfoExecutor;
import com.peter.schoolmarket.mvp.trade.detail.TradeDetailActivity;
import com.peter.schoolmarket.network.RetrofitConf;
import com.peter.schoolmarket.util.ResultInterceptor;

import java.util.List;

/**
 * Created by PetterChen on 2017/5/7.
 */

class DrawerTradePresenter implements IDrawerTradePresenter, IDrawerTradeListener {
    private Context context;
    private IDrawerTradeView view;
    private IDrawerTradeModel model;
    private int typeId = -1;
    private static int page = 1;
    private int myId;

    DrawerTradePresenter(Context context, IDrawerTradeView view) {
        this.context = context;
        this.view = view;
        model = new DrawerTradeModel();
    }

    @Override
    public void init(int typeId) {
        this.typeId = typeId;
        User user = LoginInfoExecutor.getUser(context);
        myId = user.getId();
        view.showProgress();
        model.drawerTradeDataReq(this, typeId, page, myId);
    }

    @Override
    public void refresh() {
        model.drawerTradeDataReq(this, typeId, page, myId);
    }

    @Override
    public void onComplete(final Result<List<Trade>> result) {
        view.hideProgress();
        view.hideRefresh();
        if (!ResultInterceptor.instance.resultDataHandler(result)){
            return;
        }
        switch (typeId) {
            case 0:
                initBuyingAdapter(result);
                break;
            case 1:
                initBoughtAdapter(result);
                break;
            case 2:
                initSellingAdapter(result);
                break;
            case 3:
                initSoldAdapter(result);
                break;
            default:
                break;
        }
    }

    @Override
    public void confirmComplete(Result<String> result) {
        view.hideProgress();
        switch (result.getCode()) {
            case 100://操作成功
                //loginRegisterView.loginSuccess();
                view.onSuccess(result.getData());
                view.showRefresh();
                model.drawerTradeDataReq(this, typeId, page, myId);
                break;
            case 99://网络异常或者系统错误
                //loginRegisterView.loginFailed(result.getMsg());
                view.onFail(result.getMsg());
                break;
            default:
                break;
        }
    }

    @Override
    public void cancelTradeComplete(Result<String> result) {
        view.hideProgress();
        switch (result.getCode()) {
            case 100://操作成功
                //loginRegisterView.loginSuccess();
                view.onSuccess(result.getData());
                view.showRefresh();
                model.drawerTradeDataReq(this, typeId, page, myId);
                break;
            case 99://网络异常或者系统错误
                //loginRegisterView.loginFailed(result.getMsg());
                view.onFail(result.getMsg());
                break;
            default:
                break;
        }
    }

    private void initBuyingAdapter(final Result<List<Trade>> result) {
        RecyclerCommonAdapter<?> adapter=new RecyclerCommonAdapter<Trade>(context,result.getData(), R.layout.drawer_trade_buying_item) {
            @Override
            public void convert(RecyclerViewHolder viewHolder, final Trade item) {
                viewHolder.setFrescoImg(R.id.buying_item_img, Uri.parse(AppConf.BASE_URL +
                        RetrofitConf.base_img + item.getImgUrl()));
                viewHolder.setText(R.id.buying_item_name, item.getTitle());
                viewHolder.setText(R.id.buying_item_now_price, "友情价 ￥" + item.getNowPrice());
                viewHolder.setText(R.id.buying_item_original_price, "原价 ￥" + item.getOriginalPrice());
                viewHolder.setOnClickListener(R.id.buying_item_show_detail, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, TradeDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("trade", item);
                        bundle.putBoolean("isShow", false);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
                if (item.getAuthorId() == myId) {
                    if (item.getStatus() == 3) {//2==确认收款成功
                        viewHolder.getView(R.id.buying_confirm_cancel).setVisibility(View.GONE);
                        viewHolder.getView(R.id.buying_confirm_later).setVisibility(View.VISIBLE);
                        viewHolder.setText(R.id.buying_confirm_later_text, "等待对方确认收货");
                    } else {
                        viewHolder.setText(R.id.buying_item_confirm, "确认收款");
                        viewHolder.setOnClickListener(R.id.buying_item_confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                view.showProgress();
                                model.confirmReceiveMoney(DrawerTradePresenter.this, myId, item.getId());
                                //Toast.makeText(context, "确认收款", Toast.LENGTH_SHORT).show();
                            }
                        });
                        viewHolder.setOnClickListener(R.id.buying_item_cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Toast.makeText(context, "cancel", Toast.LENGTH_SHORT).show();
                                view.showProgress();
                                model.cancelOrder(DrawerTradePresenter.this, item.getId());
                            }
                        });
                    }
                } else {
                    if (item.getStatus() == 2) {
                        viewHolder.getView(R.id.buying_confirm_cancel).setVisibility(View.GONE);
                        viewHolder.getView(R.id.buying_confirm_later).setVisibility(View.VISIBLE);
                        viewHolder.setText(R.id.buying_confirm_later_text, "等待对方确认收款");
                    } else {
                        viewHolder.setText(R.id.buying_item_confirm, "确认收货");
                        viewHolder.setOnClickListener(R.id.buying_item_confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                view.showProgress();
                                model.confirmReceiveTrade(DrawerTradePresenter.this, myId, item.getId());

                                //Toast.makeText(context, "确认收货", Toast.LENGTH_SHORT).show();
                            }
                        });
                        viewHolder.setOnClickListener(R.id.buying_item_cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Toast.makeText(context, "cancel", Toast.LENGTH_SHORT).show();
                                view.showProgress();
                                model.cancelOrder(DrawerTradePresenter.this, item.getId());
                            }
                        });
                    }
                }
            }
        };
        view.loadDataSuccess(adapter);
    }
    private void initBoughtAdapter(final Result<List<Trade>> result) {
        RecyclerCommonAdapter<?> adapter=new RecyclerCommonAdapter<Trade>(context,result.getData(), R.layout.drawer_trade_bought_item) {
            @Override
            public void convert(RecyclerViewHolder viewHolder, Trade item) {
                viewHolder.setFrescoImg(R.id.bought_item_img, Uri.parse(AppConf.BASE_URL +
                        RetrofitConf.base_img + item.getImgUrl()));
                viewHolder.setText(R.id.bought_item_name, item.getTitle());
                viewHolder.setText(R.id.bought_item_now_price, "友情价 ￥" + item.getNowPrice());
                viewHolder.setText(R.id.bought_item_original_price, "原价 ￥" + item.getOriginalPrice());
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
                bundle.putBoolean("isShow", false);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }
    private void initSellingAdapter(final Result<List<Trade>> result) {
        RecyclerCommonAdapter<?> adapter=new RecyclerCommonAdapter<Trade>(context,result.getData(), R.layout.drawer_trade_selling_item) {
            @Override
            public void convert(RecyclerViewHolder viewHolder, final Trade item) {
                viewHolder.setFrescoImg(R.id.selling_item_img, Uri.parse(AppConf.BASE_URL +
                        RetrofitConf.base_img + item.getImgUrl()));
                viewHolder.setText(R.id.selling_item_name, item.getTitle());
                viewHolder.setText(R.id.selling_item_now_price, "友情价 ￥" + item.getNowPrice());
                viewHolder.setText(R.id.selling_item_original_price, "原价 ￥" + item.getOriginalPrice());
                viewHolder.setOnClickListener(R.id.selling_item_show_detail, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, TradeDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("trade", item);
                        bundle.putBoolean("isShow", false);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                });
                viewHolder.setOnClickListener(R.id.selling_item_cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //商品下架
                        view.showProgress();
                        model.cancelTradeReq(DrawerTradePresenter.this, item.getId());
                    }
                });
            }
        };
        view.loadDataSuccess(adapter);
    }
    private void initSoldAdapter(final Result<List<Trade>> result) {
        RecyclerCommonAdapter<?> adapter=new RecyclerCommonAdapter<Trade>(context,result.getData(), R.layout.drawer_trade_sold_item) {
            @Override
            public void convert(RecyclerViewHolder viewHolder, Trade item) {
                viewHolder.setFrescoImg(R.id.sold_item_img, Uri.parse(AppConf.BASE_URL +
                        RetrofitConf.base_img + item.getImgUrl()));
                viewHolder.setText(R.id.sold_item_name, item.getTitle());
                viewHolder.setText(R.id.sold_item_now_price, "友情价 ￥" + item.getNowPrice());
                viewHolder.setText(R.id.sold_item_original_price, "原价 ￥" + item.getOriginalPrice());
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
                bundle.putBoolean("isShow", false);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }
}
