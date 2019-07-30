package com.ch.myframe.dragger.component;

import android.app.Activity;

import com.ch.myframe.dragger.FragmentScope;
import com.ch.myframe.dragger.module.FragmentModule;


import dagger.Component;

/**
 * @author Administrator
 */
@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

}
