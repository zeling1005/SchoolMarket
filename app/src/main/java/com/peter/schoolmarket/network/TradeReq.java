package com.peter.schoolmarket.network;

import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Trade;
import com.peter.schoolmarket.data.pojo.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by PetterChen on 2017/4/28.
 */

public interface TradeReq {
    /**
     * 发布商品
     */
    @Multipart
    @POST(RetrofitConf.create_trade)
    Observable<Result<String>> addTrade(
            @Part("trade") RequestBody trade,
            @Part("photo") RequestBody photo);

    /**
     * 获取在售商品列表
     */
    @GET(RetrofitConf.get_trades)
    Observable<Result<List<Trade>>> getFindTrades(
            @Query("page") int page,
            @Query("size") int size
    );

    /**
     * 根据分类获取在售商品列表
     */
    @POST(RetrofitConf.get_tag_trades)
    @FormUrlEncoded
    Observable<Result<List<Trade>>> getTrades(
            @Field("tagName")  String tagName,
            @Field("page") int page,
            @Field("size") int size
    );

    /**
     * 获取商品列表
     */
    @POST(RetrofitConf.get_drawer_trades)
    @FormUrlEncoded
    Observable<Result<List<Trade>>> getDrawerTrades(
            @Field("typeId") int typeId,
            @Field("page") int page,
            @Field("size") int size,
            @Field("myId") int myId
    );

    /**
     * 下单
     */
    @POST(RetrofitConf.place_order)
    @FormUrlEncoded
    Observable<Result<String>> placeOrder(
            @Field("userId") int userId,
            @Field("tradeId") int tradeId
    );

    /**
     * 确认收货
     */
    @POST(RetrofitConf.confirm_trade)
    @FormUrlEncoded
    Observable<Result<String>> confirmTrade(
            @Field("userId") int userId,
            @Field("tradeId") int tradeId
    );

    /**
     * 确认收款
     * @param userId 卖家用户id
     * @param tradeId 商品id
     */
    @POST(RetrofitConf.confirm_money)
    @FormUrlEncoded
    Observable<Result<String>> confirmMoney(
            @Field("userId") int userId,
            @Field("tradeId") int tradeId
    );

    /**
     * 取消订单
     * @param tradeId 商品id
     */
    @POST(RetrofitConf.cancel_order)
    @FormUrlEncoded
    Observable<Result<String>> cancelOrder(
            @Field("tradeId") int tradeId
    );

    /**
     * 商品下架
     * @param tradeId 商品id
     */
    @POST(RetrofitConf.cancel_trade)
    @FormUrlEncoded
    Observable<Result<String>> cancelTrade(
            @Field("tradeId") int tradeId
    );
}