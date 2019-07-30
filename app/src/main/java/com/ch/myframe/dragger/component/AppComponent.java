package com.ch.myframe.dragger.component;


import com.ch.myframe.MyApplication;
import com.ch.myframe.dragger.ContextLife;
import com.ch.myframe.dragger.module.AppModule;


import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Administrator
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    /**
     * 提供App的Context
     * @return
     */
    @ContextLife("Application")
    MyApplication getContext();

}
