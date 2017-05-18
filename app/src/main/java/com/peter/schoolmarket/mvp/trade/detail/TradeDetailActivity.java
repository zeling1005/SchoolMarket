package com.peter.schoolmarket.mvp.trade.detail;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.application.AppConf;
import com.peter.schoolmarket.data.pojo.Msg;
import com.peter.schoolmarket.data.pojo.Trade;
import com.peter.schoolmarket.data.pojo.User;
import com.peter.schoolmarket.data.storage.LoginInfoExecutor;
import com.peter.schoolmarket.mvp.base.BaseActivity;
import com.peter.schoolmarket.mvp.msg.ManageMsgCount;
import com.peter.schoolmarket.mvp.msg.MsgFragment;
import com.peter.schoolmarket.network.RetrofitConf;

import io.realm.Realm;

/**
 * Created by PetterChen on 2017/4/30.
 */

public class TradeDetailActivity extends BaseActivity implements ITradeDetailView {
    private SimpleDraweeView goBack;
    private SimpleDraweeView sendMsg;
    private SimpleDraweeView authorImg;
    private TextView authorName;
    private SimpleDraweeView img;
    private TextView name;
    private TextView nowPrice;
    private TextView originalPrice;
    private TextView phone;
    private TextView describe;
    private TextView submit;
    private FrameLayout submitParentLayout;
    private MaterialDialog progress;
    private TradeDetailPresenter presenter;
    private Trade trade;
    private boolean isShow = true;
    Realm realm;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.trade_detail_activity);
        initVariate();
        manageVariate();
    }

    private void initVariate() {
        goBack = (SimpleDraweeView) findViewById(R.id.trade_detail_toolbar_back);
        sendMsg = (SimpleDraweeView) findViewById(R.id.trade_detail_send_msg);
        authorImg = (SimpleDraweeView) findViewById(R.id.trade_detail_author_img);
        authorName = (TextView) findViewById(R.id.trade_detail_author_name);
        img = (SimpleDraweeView) findViewById(R.id.trade_detail_img);
        name = (TextView) findViewById(R.id.trade_detail_name);
        nowPrice = (TextView) findViewById(R.id.trade_detail_now_price);
        originalPrice = (TextView) findViewById(R.id.trade_detail_original_price);
        phone = (TextView) findViewById(R.id.trade_detail_phone);
        describe = (TextView) findViewById(R.id.trade_detail_describe);
        submit = (TextView) findViewById(R.id.trade_detail_submit);
        submitParentLayout = (FrameLayout) findViewById(R.id.trade_detail_submit_layout);
        progress = new MaterialDialog.Builder(TradeDetailActivity.this)
                .content("正在下单...")
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .title("请稍等")
                .build();
        presenter = new TradeDetailPresenter(this, this);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        trade = (Trade) bundle.getSerializable("trade");
        isShow = bundle.getBoolean("isShow", true);
        realm = Realm.getDefaultInstance();
    }

    private void manageVariate() {
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置成返回上一个activity或者fragment
                (TradeDetailActivity.this).finish();
            }
        });
        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到信息发送,发送一条广播通知买者和卖者,建立消息入口,消息activity监听实时消息.
                Toast.makeText(TradeDetailActivity.this, "jump send msg", Toast.LENGTH_SHORT).show();
            }
        });
        sendMsg.setVisibility(View.GONE);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int myId= LoginInfoExecutor.getUser(TradeDetailActivity.this).getId();
                if (trade.getAuthorId() == myId){
                    Toast.makeText(TradeDetailActivity.this, "无法购买自己的商品！", Toast.LENGTH_SHORT).show();
                } else {
                    new MaterialDialog.Builder(TradeDetailActivity.this)
                            .title("确认下单？")
                            .content("请在确认下单之后联系卖家！")
                            .positiveText("确定")
                            .negativeText("取消")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    //执行下单操作
                                    /*final Msg msg =new Msg();
                                    msg.setTitle("下单消息");
                                    msg.setContent("恭喜您成功下单，订单商品为：\"" + trade.getTitle() + "\"");
                                    msg.setCreateTime(System.currentTimeMillis());
                                    realm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            int id = ManageMsgCount.getInstance().getCount(TradeDetailActivity.this);
                                            msg.setId(id);
                                            realm.copyToRealm(msg);
                                            ManageMsgCount.getInstance().saveCount(TradeDetailActivity.this, id + 1);
                                        }
                                    });
                                    Intent intent = new Intent(MsgFragment.MY_BROADCAST_ACTION);
                                    sendBroadcast(intent);*/
                                    presenter.placeOrder(trade.getId());
                                    //Toast.makeText(TradeDetailActivity.this, "submit", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                }
            }
        });
        presenter.loadTradeData(trade);
        if (!isShow) {
            submitParentLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showPlaceOrderReqSuc(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG ).show();
    }

    @Override
    public void showPlaceOrderReqFail() {
        Toast.makeText(this, "网络异常或者系统错误", Toast.LENGTH_LONG ).show();
    }

    @Override
    public void loadTradeSuccess(Trade trade) {
        User user = realm.where(User.class).equalTo("id",trade.getAuthorId()).findFirst();
        String temp = "";
        if (user != null) {
            authorImg.setImageURI(AppConf.BASE_URL + RetrofitConf.base_img + user.getImgUrl());
            authorName.setText(user.getUsername());
            phone.setText(user.getPhone());
        }
        img.setImageURI(AppConf.BASE_URL + RetrofitConf.base_img + trade.getImgUrl());
        temp = getResources().getString(R.string.trade_detail_name) + trade.getTitle();
        name.setText(temp);
        temp = getResources().getString(R.string.trade_detail_now_price) + trade.getNowPrice();
        nowPrice.setText(temp);
        temp = getResources().getString(R.string.trade_detail_original_price) + trade.getOriginalPrice();
        originalPrice.setText(temp);
        temp = getResources().getString(R.string.trade_detail_describe) + trade.getDescribe();
        describe.setText(temp);
    }

    @Override
    public void showProgress() {
        if (!progress.isShowing()) {
            progress.show();
        }
    }

    @Override
    public void hideProgress() {
        if (progress.isShowing()) {
            progress.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
