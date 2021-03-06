package com.ch.myframe;

/**
 * Created by Administrator on 2017/4/11.
 */

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.ch.myframe.dragger.component.AppComponent;
import com.ch.myframe.dragger.component.DaggerAppComponent;
import com.ch.myframe.dragger.module.AppModule;
import com.ch.myframe.utils.CrashHandler;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;
import java.util.List;


public class MyApplication extends Application {
    private List<Activity> oList;//用于存放所有启动的Activity的集合
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext(), this);
        oList = new ArrayList<Activity>();
//        //bugly
//        CrashReport.initCrashReport(getApplicationContext(), "ae58aa2931", false);
//        //LeakCanary
//        LeakCanary.install(this);
    }

    public static AppComponent getAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .build();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        try {
            System.exit(0);
        } catch (Exception e) {
            e.toString();
        }
    }


    /**
     * 添加Activity
     */
    public void addActivity_(Activity activity) {
        // 判断当前集合中不存在该Activity
        if (!oList.contains(activity)) {
            oList.add(activity);//把当前Activity添加到集合中
        }
    }

    /**
     * 销毁单个Activity
     */
    public void removeActivity_(Activity activity) {
        //判断当前集合中存在该Activity
        if (oList.contains(activity)) {
            oList.remove(activity);//从集合中移除
            activity.finish();//销毁当前Activity
        }
    }

    /**
     * 销毁所有的Activity
     */
    public void removeALLActivity_() {
        //通过循环，把集合中的所有Activity销毁
        for (Activity activity : oList) {
            activity.finish();
        }
    }
}