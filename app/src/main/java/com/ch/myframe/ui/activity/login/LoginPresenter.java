package com.ch.myframe.ui.activity.login;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import android.content.Context;

import com.ch.myframe.base.BaseMvpPresenter;
import com.ch.myframe.bean.User;
import com.ch.myframe.http.DefaultObserver;
import com.ch.myframe.http.RetrofitUtils;
import com.ch.myframe.http.RxSchedulers;
import com.ch.myframe.response.LoginResponse;
import com.ch.myframe.service.ApiService;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import javax.inject.Inject;

/**
 * @Author: Administrator
 * @Time: 2019/7/29 16:27
 * @Company：ch
 * @Description: 功能描述
 */
public class LoginPresenter extends BaseMvpPresenter<LoginContract.View> implements LoginContract.Presenter {

    @Inject
    public LoginPresenter() {

    }

    public void login(String userName, String password) {
//        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
//            mView.httpError("请输入用户名密码");
//            return;
//        }
        RetrofitUtils.getInstance().create(ApiService.class)
                .login("1","20")
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(new DefaultObserver<User>((Context) mView) {
                    @Override
                    public void onSuccess(User result) {
                        mView.httpCallback(result);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mView.httpError(e.toString());
                    }
                });
    }
}
