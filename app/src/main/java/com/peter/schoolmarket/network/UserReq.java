package com.peter.schoolmarket.network;

import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Msg;
import com.peter.schoolmarket.data.pojo.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by PetterChen on 2017/4/13.
 */

public interface UserReq {
    /**
     * 用户登录并获取用户信息
     */
    @POST(RetrofitConf.login)
    @FormUrlEncoded
    Observable<Result<User>> login(
            @Field("username") String username,
            @Field("password") String password);

    /**
     * 用户注册请求
     */
    @POST(RetrofitConf.register)
    @FormUrlEncoded
    Observable<Result<String>> register(
            @Field("username")  String username,
            @Field("password")  String password);

    /**
     * 注册下一步，设置学校和手机号
     */
    @PUT(RetrofitConf.register_next)
    @FormUrlEncoded
    Observable<Result<User>> registerNext(
            @Path("userId")  String user_id,
            @Field("phone")  String phone);

    /*---------------------------我是分割线---------------------------------*/

    /**
     * 获取token
     */
    @POST(RetrofitConf.auth)
    @FormUrlEncoded
    Call<String> auth(
            @Field("username") String username,
            @Field("password") String password);

    /**
     * 获取用户发起的志愿队列表
     */
    @GET(RetrofitConf.get_user_team)
    Observable<Result<List<User>>> getUserTeams(
            @Path("userId")  String user_id
    );

    /**
     * 获取用户所有交易信息,实现使用后台轮询
     */
    @GET(RetrofitConf.get_user_messages)
    Observable<Result<List<Msg>>> getUserMsgs(
            @Path("userId")  String user_id,
            @Query("msgNum") int msgNum,
            @Query("page") int page,
            @Query("size") int size
    );


    /**
     * 获取用户所有商品列表
     * 根据type区分商品类型
     * 0：待确认；1：待售；2：已买；3：已卖；4：捐赠
     */
    @GET(RetrofitConf.get_user_trades)
    Observable<Result<List<User>>> getUserTrades(
            @Path("userId")  String user_id,
            @Query("type") int type
    );

    /**
     * 捐赠用户的商品
     */
    @PUT(RetrofitConf.donate_user_trade)
    @FormUrlEncoded
    Observable<Result<String>> donateUserTrade(
            @Path("userId")  String user_id,
            @Field("tradeId")  String tradeId,
            @Field("tradeImg")  String tradeImg,
            @Field("team")  String team
    );
}
