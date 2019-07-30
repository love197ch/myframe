package com.ch.myframe.dragger.component;

import android.app.Activity;

import com.ch.myframe.dragger.ActivityScope;
import com.ch.myframe.dragger.module.ActivityModule;
import com.ch.myframe.ui.activity.login.LoginActivity;

import dagger.Component;

/**
 * @author Administrator
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(LoginActivity loginActivity);


}
