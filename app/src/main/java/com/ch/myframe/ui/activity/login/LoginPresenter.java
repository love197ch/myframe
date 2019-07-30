package com.ch.myframe.ui.activity.login;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.ch.myframe.base.BaseMvpPresenter;
import com.ch.myframe.http.DefaultObserver;
import com.ch.myframe.http.RetrofitUtils;
import com.ch.myframe.http.RxSchedulers;
import com.ch.myframe.response.LoginResponse;
import com.ch.myframe.service.ApiService;
import com.ch.myframe.utils.StaticVariable;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
        ApiService api = RetrofitUtils.getInstance(StaticVariable.URL).create(ApiService.class);
        api.login("00d91e8e0cca2b76f515926a36db68f5", "13594347817", "123456")
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(new DefaultObserver<LoginResponse>((Context) mView) {
                    @Override
                    public void onSuccess(LoginResponse result) {
                        mView.httpCallback(result.getData());
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mView.httpError(e.toString());
                    }
                });
    }
}
