package com.peter.schoolmarket.mvp.trade.add;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Trade;
import com.peter.schoolmarket.data.pojo.User;
import com.peter.schoolmarket.data.storage.LoginInfoExecutor;
import com.peter.schoolmarket.network.NetReturn;
import com.peter.schoolmarket.network.RetrofitUtils;
import com.peter.schoolmarket.util.ResultInterceptor;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by PetterChen on 2017/4/30.
 */

class TradeAddPresenter implements ITradeAddPresenter, ITradeAddListener {

    private ITradeAddModel model;
    private ITradeAddView view;
    private Context context;

    TradeAddPresenter(Context context, ITradeAddView view) {
        this.context = context;
        this.view = view;
        this.model=new TradeAddModel();
    }

    @Override
    public void addTradeResult(Result<String> result) {
        view.hideProgress();
        if (ResultInterceptor.instance.resultHandler(result)){
            view.tradeAddSuccess();
        }else {
            view.whenFail(NetReturn.SERVER_ERROR.msg());
        }
    }

    @Override
    public void releaseTrade(String picUploadUrl, EditText titleContent, EditText nowPrice,
                             EditText originalPrice, EditText descContent, TextView tag) {
        Trade trade = checkForm(picUploadUrl,titleContent,nowPrice,originalPrice,descContent,tag);

        if (!trade.isReleaseCheck()){
            return;
        }
        view.showProgress();
        trade.setStatus(0);//状态,0代表商品待售
        trade.setCreateTime(System.currentTimeMillis());//设置商品发布时间
        MultipartBody.Part part= RetrofitUtils.fileToMultipartBodyPart(picUploadUrl);
        String tradeJsonStr=new Gson().toJson(trade);
        RequestBody tradeJson=RetrofitUtils.createPartFromString(tradeJsonStr);
        model.addTradeReq(tradeJson,part,this);
    }

    private Trade checkForm(String picUploadUrl, EditText title, EditText nowPrice,
                            EditText originalPrice, EditText desc, TextView tag){
        Trade trade=new Trade();
        trade.setReleaseCheck(false);

        String titleData=title.getText().toString().trim();
        if (titleData.isEmpty()){
            view.whenFail("标题不可以为空");
            return trade;
        }
        trade.setTitle(titleData);

        String nowPriceData=nowPrice.getText().toString().trim();
        if (nowPriceData.isEmpty()){
            view.whenFail("价格不可以为空");
            return trade;
        }
        long nowPriceValue=Long.parseLong(nowPriceData);
        trade.setNowPrice(nowPriceValue);

        String originalPriceData=originalPrice.getText().toString().trim();
        if (originalPriceData.isEmpty()){
            view.whenFail("原价不可以为空");
            return trade;
        }
        long originalPriceValue=Long.parseLong(originalPriceData);
        trade.setNowPrice(originalPriceValue);

        String descData=desc.getText().toString().trim();
        if (descData.isEmpty()){
            view.whenFail("描述不可以为空");
            return trade;
        }
        trade.setDescribe(descData);

        String tagData=tag.getText().toString().trim();
        if (tagData.equals("设置分类")){
            view.whenFail("请选择商品分类");
            return trade;
        }
        trade.setTagName(tagData);

        if (picUploadUrl == null || picUploadUrl.equals("")){
            view.whenFail("请选择配图");
            return trade;
        }
        trade.setImgUrls(picUploadUrl);

        User authorOld= LoginInfoExecutor.getUser(context);
        /*if (authorOld==null || authorOld.getId()==null){
            return trade;
        }
        trade.setAuthorId(authorOld.getId());*/
        trade.setAuthorName(authorOld.getUsername());
        trade.setAuthorImg(authorOld.getAvatarUrl());
        trade.setAuthorPhone(authorOld.getPhone());

        trade.setReleaseCheck(true);
        return trade;
    }
}
