package com.peter.schoolmarket.network;

import com.peter.schoolmarket.BuildConfig;
import com.peter.schoolmarket.application.AppConf;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

import static com.peter.schoolmarket.network.RetrofitConf.DEFAULT_TIMEOUT;

/**
 * Created by PetterChen on 2017/4/18.
 */

public class ReqExecutor {
    private UserReq userReq;


    private ReqExecutor(){}

    private static class ReqExecutorBuilder {
        private static ReqExecutor instance = new ReqExecutor();
    }

    public static ReqExecutor INSTANCE() {
        return ReqExecutorBuilder.instance;
    }
    private Retrofit getRetrofit(Converter.Factory factory){
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {//打印http日志
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(loggingInterceptor);
        }

        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        return new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(AppConf.BASE_URL)
                .build();
    }

    private Retrofit getGsonRetrofit(){
        return getRetrofit(GsonConverterFactory.create());
    }

    public UserReq userReq(){
        if (userReq==null){
            userReq=getGsonRetrofit().create(UserReq.class);
        }
        return userReq;
    }
}