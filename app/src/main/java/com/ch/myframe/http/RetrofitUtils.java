package com.ch.myframe.http;

import com.ch.myframe.http.fastjson.FastJsonConverterFactory;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


public class RetrofitUtils {
    private static Retrofit mRetrofit;

    private static Retrofit getRetrofit(String url) {
        // 创建 OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(10, TimeUnit.SECONDS);//写操作超时时间
        builder.readTimeout(10, TimeUnit.SECONDS);//读操作超时时间
        // 添加公共参数拦截器
        HttpCommonInterceptor commonInterceptor = new HttpCommonInterceptor.Builder()
                .addHeaderParams("paltform", "android")
                .addHeaderParams("userToken", "1234343434dfdfd3434")
                .addHeaderParams("userId", "123445")
                .build();
        builder.addInterceptor(commonInterceptor);

        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
//                .baseUrl(StaticVariable.URL)   在基础URL不变情况下可以使用这句话
                .baseUrl(url)
                .addConverterFactory(FastJsonConverterFactory.create())//重点是这句话
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return mRetrofit;
    }

    public static synchronized Retrofit getInstance(String url) {
        //在基础URL不变情况下可以使用单例模式
        if (mRetrofit == null) {
            try {
                mRetrofit = getRetrofit(url);
            } catch (Exception e) {
                e.toString();
            }
        }
        return mRetrofit;
    }

    public static synchronized Retrofit changeUrl(String url) {
        try {
            mRetrofit = getRetrofit(url);
        } catch (Exception e) {
            e.toString();
        }
        return mRetrofit;
    }
}
