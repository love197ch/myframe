package com.ch.myframe.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ch.myframe.R;
import com.ch.myframe.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Author: Administrator
 * @Time: 2019/7/30 15:18
 * @Company：ch
 * @Description: 首页
 */
public class ConferenceFragment extends BaseFragment {

    private Unbinder unbinder;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conference, container, false);
        ButterKnife.bind(this, view);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

}
