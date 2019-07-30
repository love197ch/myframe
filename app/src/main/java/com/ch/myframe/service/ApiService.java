package com.ch.myframe.service;


import com.ch.myframe.response.LoginResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {

    @FormUrlEncoded
    @POST("/login")
    Observable<LoginResponse> login(@Field("key") String key,
                                    @Field("phone") String phone,
                                    @Field("passwd") String passwd);


}
