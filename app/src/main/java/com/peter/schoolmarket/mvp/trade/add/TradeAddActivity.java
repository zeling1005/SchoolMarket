package com.peter.schoolmarket.mvp.trade.add;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;
import com.peter.schoolmarket.R;
import com.peter.schoolmarket.data.pojo.TradeTag;
import com.peter.schoolmarket.mvp.base.BaseActivity;
import com.peter.schoolmarket.mvp.main.MainActivity;
import com.peter.schoolmarket.util.TradeTagUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import cn.finalteam.rxgalleryfinal.utils.Logger;

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
    ImageView cancel;
    ImageView submit;

    MaterialDialog progress;
    TradeAddPresenter presenter;
    private List<String> tags;
    private String picUploadUrl;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        addImg = (SimpleDraweeView) findViewById(R.id.trade_add_img);
        selectTag = (TextView) findViewById(R.id.trade_add_tag_select);
        tradeTitle = (EditText) findViewById(R.id.trade_add_title_content);
        tradeNowPrice = (EditText) findViewById(R.id.trade_add_now_price_content);
        tradeOriginalPrice = (EditText) findViewById(R.id.trade_add_original_price_content);
        tradeDesc = (EditText) findViewById(R.id.trade_add_describe_content);
        cancel = (ImageView) findViewById(R.id.trade_add_cancel);
        submit = (ImageView) findViewById(R.id.trade_add_submit);

        progress = new MaterialDialog.Builder(TradeAddActivity.this)
                .content("正在处理...")
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .title("请稍等")
                .build();
        presenter = new TradeAddPresenter(this, this);

        tags = new ArrayList<String>();

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
                //返回到上一个界面
                /*Intent intent=new Intent(TradeAddActivity.this,MainActivity.class);
                startActivity(intent);
                finish();*/
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
                picSelector();
            }
        });
    }

    private void initTags() {
        List<TradeTag> tradeTags = TradeTagUtils.getTradeTag();
        for (TradeTag tag : tradeTags) {
            tags.add(tag.getName());
        }
    }

    private void picSelector(){
        try {
            //自定义方法的单选
            RxGalleryFinal
                    .with(TradeAddActivity.this)
                    .image()
                    .radio()
                    .crop()
                    .imageLoader(ImageLoaderType.FRESCO)
                    .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
                        @Override
                        protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                            //图片选择结果
                            picUploadUrl = imageRadioResultEvent.getResult().getCropPath();
                            /*
                            File file=new File(filePath);
                            if (!file.exists()){
                                return null;
                            }
                            // TODO: 16-11-30  没有判断file的类型
                            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
                            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(),
                            requestBody);*/
                        }
                    })
                    .openGallery();
        } catch (Exception e) {
            Logger.w("图片选择器出现异常:"+e.getLocalizedMessage());
        }
    }

    @Override
    public void tradeAddSuccess() {
        new SuperToast(TradeAddActivity.this)
                .setText("发布成功")
                .setDuration(Style.DURATION_LONG)
                .setColor(PaletteUtils.getTransparentColor(PaletteUtils.MATERIAL_GREEN))
                .setAnimations(Style.ANIMATIONS_POP)
                .show();
        //返回到分类界面
        /*Intent intent=new Intent(TradeAddActivity.this, MainActivity.class);
        startActivity(intent);
        finish();*/
    }

    @Override
    public void whenFail(String errorMsg) {
        new SuperToast(TradeAddActivity.this)
                .setText(errorMsg)
                .setDuration(Style.DURATION_LONG)
                .setColor(PaletteUtils.getTransparentColor(PaletteUtils.MATERIAL_RED))
                .setAnimations(Style.ANIMATIONS_FLY)
                .show();
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
