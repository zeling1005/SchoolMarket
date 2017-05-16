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

    int GET_TAG=1;
    int GET_NAME=2;


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

    //-------------我是分割线----------------------




    /**
     *
     */
    @POST(RetrofitConf.get_author_info)
    @FormUrlEncoded
    Observable<Result<User>> getAuthorInfo(
            @Field("authorId") String authorId);


    //----------我是分割线-------------------//

    //创建交易(发起购买商品请求)
    @POST(RetrofitConf.create_deal)
    @FormUrlEncoded
    Observable<Result<String>> createDeal(
            @Field("fromId") String fromId,
            @Field("fromName") String fromName,
            @Field("fromAvatar") String fromAvatar,
            @Field("tradeId") String tradeId
    );

    /**
     * 获取分类列表请求(List<TradeTag>)
     *
     * kind设置为1
     */
    /*@GET(ApiConf.get_tags)
    Observable<Result<List<TradeTag>>> tradeTags(@Query("type") int type);*/


    /**
     * 获取分类名称列表请求(List<String>)
     *
     * kind设置为2
     */
    /*@GET(ApiConf.get_tags)
    Observable<Result<List<String>>> tagNames(@Query("type") int type);*/

    /**
     * 根据商品Id获取商品详细信息
     */
    /*@GET(ApiConf.get_trade)
    Observable<Result<Trade>> tradeDetail(@Path("tradeId") String tradeId);*/

    /**
     * 根据学校名称获取本校在售商品列表
     */
    /*@GET(ApiConf.get_school_trades)
    Observable<Result<List<Trade>>> getSchoolTrades(
            @Path("schoolName")  String school,
            @Query("page") int page,
            @Query("size") int size
    );*/

    /**
     * 根据志愿队名称获取收到捐赠的商品列表
     */
    /*@GET(ApiConf.get_team_trades)
    Observable<Result<List<Trade>>> getTeamTrades(
            @Path("teamName")  String school,
            @Query("page") int page,
            @Query("size") int size
    );*/
}