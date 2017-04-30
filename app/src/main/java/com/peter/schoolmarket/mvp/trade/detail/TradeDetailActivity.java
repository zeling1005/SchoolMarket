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
    private TradeDetailPresenter presenter;
    private ImageView goBack;
    private SimpleDraweeView img;
    private TextView name;
    private TextView nowPrice;
    private TextView originalPrice;
    private TextView phone;
    private TextView describe;
    private MaterialDialog progress;
    private TextView submit;
    private Trade trade;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        goBack = (ImageView) findViewById(R.id.trade_detail_toolbar_back);
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
        //Person person = (Person) getIntent().getSerializableExtra("person_data");
        Intent intent=getIntent();
        trade = (Trade) intent.getSerializableExtra("trade_data");
        presenter.loadTradeData(trade);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置成返回上一个activity或者fragment
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myId= LoginInfoExecutor.getUser(TradeDetailActivity.this).getId();
                if (!(trade.getAuthor().getId()).equals(myId)){
                    new MaterialDialog.Builder(TradeDetailActivity.this)
                            .title("确认下单？")
                            .content("请在确认下单之后联系卖家！")
                            .positiveText("确定")
                            .negativeText("取消")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    presenter.placeOrder(trade.getId());
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
        img.setImageURI(trade.getImgUrls());
        name.setText(trade.getTitle());
        nowPrice.setText("友情价:￥" + trade.getNowPrice());
        originalPrice.setText("原价:￥" + trade.getOriginalPrice());
        phone.setText(trade.getAuthor().getPhone());
        describe.setText(trade.getDescribe());
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
