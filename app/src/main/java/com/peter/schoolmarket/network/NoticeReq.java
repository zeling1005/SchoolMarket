package com.peter.schoolmarket.network;

import com.peter.schoolmarket.data.dto.Result;
import com.peter.schoolmarket.data.pojo.Notice;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by PetterChen on 2017/5/5.
 */

public interface NoticeReq {
    /**
     * 获取在售商品列表
     */
    @GET(RetrofitConf.get_notices_data)
    Observable<Result<List<Notice>>> getNoticesData(
            @Query("page") int page,
            @Query("size") int size
    );

    /**
     * 发布商品
     */
    @POST(RetrofitConf.create_notice)
    Observable<Result<String>> addNotice(
            @Part("notice") RequestBody notice);
}
