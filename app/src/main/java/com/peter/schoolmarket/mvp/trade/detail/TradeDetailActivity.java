package com.peter.schoolmarket.mvp.trade.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.data.pojo.Trade;
import com.peter.schoolmarket.data.storage.LoginInfoExecutor;
import com.peter.schoolmarket.mvp.base.BaseActivity;

/**
 * Created by PetterChen on 2017/4/30.
 */

public class TradeDetailActivity extends BaseActivity implements ITradeDetailView {
    private ImageView goBack;
    private ImageView sendMsg;
    private SimpleDraweeView authorImg;
    private TextView authorName;
    private SimpleDraweeView img;
    private TextView name;
    private TextView nowPrice;
    private TextView originalPrice;
    private TextView phone;
    private TextView describe;
    private TextView submit;
    private MaterialDialog progress;
    private TradeDetailPresenter presenter;
    private Trade trade;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.trade_detail_activity);

        goBack = (ImageView) findViewById(R.id.trade_detail_toolbar_back);
        sendMsg = (ImageView) findViewById(R.id.trade_detail_send_msg);
        authorImg = (SimpleDraweeView) findViewById(R.id.trade_detail_author_img);
        authorName = (TextView) findViewById(R.id.trade_detail_author_name);
        img = (SimpleDraweeView) findViewById(R.id.trade_detail_img);
        name = (TextView) findViewById(R.id.trade_detail_name);
        nowPrice = (TextView) findViewById(R.id.trade_detail_now_price);
        originalPrice = (TextView) findViewById(R.id.trade_detail_original_price);
        phone = (TextView) findViewById(R.id.trade_detail_phone);
        describe = (TextView) findViewById(R.id.trade_detail_describe);
        submit = (TextView) findViewById(R.id.trade_detail_submit);
        progress = new MaterialDialog.Builder(TradeDetailActivity.this)
                .content("正在下单...")
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .title("请稍等")
                .build();
        presenter = new TradeDetailPresenter(this, this);

        Intent intent=getIntent();
        trade = (Trade) intent.getSerializableExtra("trade");

        presenter.loadTradeData(trade);
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
                //跳转到信息发送
                Toast.makeText(TradeDetailActivity.this, "jump send msg", Toast.LENGTH_SHORT).show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myId= LoginInfoExecutor.getUser(TradeDetailActivity.this).getId();
                if (!(trade.getAuthorId()).equals(myId)){
                    new MaterialDialog.Builder(TradeDetailActivity.this)
                            .title("确认下单？")
                            .content("请在确认下单之后联系卖家！")
                            .positiveText("确定")
                            .negativeText("取消")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    //执行下单操作
                                    //presenter.placeOrder(trade.getId());
                                    Toast.makeText(TradeDetailActivity.this, "submit", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                }
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到拨号界面
                Toast.makeText(TradeDetailActivity.this, "jump to call up", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showPlaceOrderSuc() {
        Toast.makeText(this, "下单成功", Toast.LENGTH_LONG ).show();
    }

    @Override
    public void showPlaceOrderFail() {
        Toast.makeText(this, "下单失败", Toast.LENGTH_LONG ).show();
    }

    @Override
    public void loadTradeSuccess(Trade trade) {
        String temp = "";
        authorImg.setImageURI(trade.getAuthorImg());
        authorName.setText(trade.getAuthorName());
        img.setImageURI(trade.getImgUrls());
        temp = getResources().getString(R.string.trade_detail_name) + trade.getTitle();
        name.setText(temp);
        temp = getResources().getString(R.string.trade_detail_now_price) + trade.getNowPrice();
        nowPrice.setText(temp);
        temp = getResources().getString(R.string.trade_detail_original_price) + trade.getOriginalPrice();
        originalPrice.setText(temp);
        temp = getResources().getString(R.string.trade_detail_describe) + trade.getDescribe();
        phone.setText(trade.getAuthorPhone());
        describe.setText(temp);
    }

    @Override
    public void showProgress() {
        progress.show();
    }

    @Override
    public void hideProgress() {
        progress.dismiss();
    }
}
