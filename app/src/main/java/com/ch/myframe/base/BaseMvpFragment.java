package com.ch.myframe.base;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ch.myframe.MyApplication;
import com.ch.myframe.dragger.component.DaggerFragmentComponent;
import com.ch.myframe.dragger.component.FragmentComponent;
import com.ch.myframe.dragger.module.FragmentModule;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.Nullable;

public abstract class BaseMvpFragment<P extends BaseMvpPresenter> extends BaseFragment implements BaseMvpCallback {
    @Inject
    @Nullable
    protected P mPresenter;
    protected View mRootView;
    protected Unbinder unbinder;

    protected abstract int getLayout();
    protected abstract void initInject();
    protected abstract void initViewAndData();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayout(), container, false);
        }
        unbinder = ButterKnife.bind(this, mRootView);
        initInject();
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.attachView(this);
        unbinder = ButterKnife.bind(this, view);
        initViewAndData();
    }

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

}
