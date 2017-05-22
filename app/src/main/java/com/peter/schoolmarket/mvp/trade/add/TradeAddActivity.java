package com.peter.schoolmarket.mvp.trade.add;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.data.pojo.TradeTag;
import com.peter.schoolmarket.mvp.base.BaseActivity;
import com.peter.schoolmarket.util.TradeTagUtils;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

/**
 * Created by PetterChen on 2017/4/30.
 */

public class TradeAddActivity extends BaseActivity implements ITradeAddView {
    SimpleDraweeView addImg;
    TextView selectTag;
    EditText tradeTitle;
    EditText tradeNowPrice;
    EditText tradeOriginalPrice;
    EditText tradeDesc;
    SimpleDraweeView cancel;
    SimpleDraweeView submit;

    MaterialDialog progress;
    TradeAddPresenter presenter;
    private List<String> tags;
    private String picUrl;
    private String picUploadUrl;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.trade_add_activity);

        addImg = (SimpleDraweeView) findViewById(R.id.trade_add_img);
        selectTag = (TextView) findViewById(R.id.trade_add_tag_select);
        tradeTitle = (EditText) findViewById(R.id.trade_add_title_content);
        tradeNowPrice = (EditText) findViewById(R.id.trade_add_now_price_content);
        tradeOriginalPrice = (EditText) findViewById(R.id.trade_add_original_price_content);
        tradeDesc = (EditText) findViewById(R.id.trade_add_describe_content);
        cancel = (SimpleDraweeView) findViewById(R.id.trade_add_cancel);
        submit = (SimpleDraweeView) findViewById(R.id.trade_add_submit);

        progress = new MaterialDialog.Builder(TradeAddActivity.this)
                .content("正在处理...")
                .progress(true, 0)
                .progressIndeterminateStyle(false)//是否水平进度条
                .title("请稍等")
                .build();
        presenter = new TradeAddPresenter(this, this);

        tags = new ArrayList<>();

        initTags();

        selectTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(TradeAddActivity.this)
                        .title("请选择分类")
                        .items(tags)
                        .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                selectTag.setText(text);
                                return true;
                            }
                        })
                        .positiveText("选择")
                        .show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(TradeAddActivity.this)
                        .title("放弃发布？")
                        .content("您正在发布商品，离开界面会导致发布失败！确定离开？")
                        .positiveText("确定")
                        .negativeText("取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                (TradeAddActivity.this).finish();
                            }
                        })
                        .show();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.releaseTrade(picUploadUrl, tradeTitle, tradeNowPrice, tradeOriginalPrice,
                        tradeDesc, selectTag);
            }
        });
        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxGalleryFinal
                        .with(TradeAddActivity.this)
                        .image()
                        .radio()
                        .crop()
                        .cropropCompressionQuality(40)
                        .cropWithAspectRatio(1,1)
                        .imageLoader(ImageLoaderType.FRESCO)
                        .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
                            @Override
                            protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                                String path=imageRadioResultEvent.getResult().getCropPath();
                                picUrl = "file://"+path;
                                picUploadUrl = "" + path;
                                //Log.d("TradeAdd", "limain" + picUploadUrl);
                                addImg.setImageURI(picUrl);
                            }
                        })
                        .openGallery();
                //Log.d("TradeAdd", "kai shi" + picUploadUrl);
            }
        });
    }

    private void initTags() {
        List<TradeTag> tradeTags = TradeTagUtils.getTradeTag();
        for (TradeTag tag : tradeTags) {
            tags.add(tag.getName());
        }
    }

    @Override
    public void tradeAddSuccess() {
        Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void whenFail(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        if (!progress.isShowing()){
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
    public void onBackPressed() {
        new MaterialDialog.Builder(TradeAddActivity.this)
                .title("放弃发布？")
                .content("您正在发布商品，离开界面会导致发布失败！确定离开？")
                .positiveText("确定")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        (TradeAddActivity.this).finish();
                    }
                })
                .show();
    }
}