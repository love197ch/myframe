package com.ch.myframe.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.ch.myframe.base.BaseResponse;
import com.ch.myframe.bean.User;


public class LoginResponse extends BaseResponse {


    @JSONField(name = "data")
    User data;


    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
