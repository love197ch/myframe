package com.ch.myframe.http;

import com.ch.myframe.http.fastjson.FastJsonConverterFactory;

import com.ch.myframe.utils.StaticVariable;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;


public class RetrofitUtils {
    private static volatile Retrofit mRetrofit;
    private HashMap<Class, Retrofit> mServiceHashMap = new HashMap<>();
    private ConcurrentHashMap<Class, Object> cachedApis = new ConcurrentHashMap<>();

    private static Retrofit getRetrofit() {
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
                .baseUrl(StaticVariable.URL)
                .addConverterFactory(FastJsonConverterFactory.create())//重点是这句话
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return mRetrofit;
    }

    public static Retrofit getInstance() {
        //在基础URL不变情况下可以使用单例模式
        try {
            if (mRetrofit == null) {
                synchronized (RetrofitUtils.class) {
                    mRetrofit = getRetrofit();
                }
            }
        } catch (Exception e) {
            //当基础url格式错误时会报错
            e.printStackTrace();
        }
        return mRetrofit;
    }

    private RetrofitUtils() {
        if (mRetrofit != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static synchronized Retrofit changeUrl() {
        try {
            mRetrofit = getRetrofit();
        } catch (Exception e) {
            //当基础url格式错误时会报错
            return null;
        }
        return mRetrofit;
    }
}
