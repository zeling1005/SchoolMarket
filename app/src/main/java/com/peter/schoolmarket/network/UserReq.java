package com.peter.schoolmarket.network;

import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Msg;
import com.peter.schoolmarket.data.pojo.Order;
import com.peter.schoolmarket.data.pojo.User;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
     * 注册下一步，设置手机号
     */
    @POST(RetrofitConf.register_next)
    @FormUrlEncoded
    Observable<Result<User>> registerNext(
            @Field("username")  String username,
            @Field("password")  String password,
            @Field("phone")  String phone);

    @GET(RetrofitConf.get_users)
    Observable<Result<List<User>>> getUsers();

    @POST(RetrofitConf.get_msgs)
    @FormUrlEncoded
    Observable<Result<List<Msg>>> getMsgs(
            @Field("userId")  int userId);


}
