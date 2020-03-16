package com.ch.myframe.base;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.ch.myframe.MyApplication;
import com.ch.myframe.dragger.component.ActivityComponent;
import com.ch.myframe.dragger.component.DaggerActivityComponent;
import com.ch.myframe.dragger.module.ActivityModule;


import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseMvpActivity<P extends BaseMvpPresenter> extends BaseActivity implements BaseMvpCallback{
    @Inject //drager
    @io.reactivex.annotations.Nullable
    protected P mPresenter;
    protected Unbinder unbinder;

    protected abstract int getLayout();
    protected abstract void initInject();
    protected abstract void initViewAndData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initInject();
        unbinder = ButterKnife.bind(this);
        ButterKnife.bind(this);
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        initViewAndData();
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    public P getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        unbinder.unbind();
        super.onDestroy();
    }
}
