package com.ch.myframe.base;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.ch.myframe.MyApplication;
import com.ch.myframe.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2018/1/29.
 */

public class BaseActivity extends AppCompatActivity implements LifecycleOwner {
    private MyApplication application;
    private BaseActivity oContext;
    public AlertDialog alertDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (application == null) {
            // 得到Application对象
            application = (MyApplication) getApplication();
        }
        oContext = this;// 把当前的上下文对象赋值给BaseActivity
        addActivity();// 调用添加方法
        EventBus.getDefault().register(this);
    }

    // 添加Activity方法
    public void addActivity() {
        application.addActivity_(oContext);// 调用BaseApplication的添加Activity方法
    }

    //销毁当个Activity方法
    public void removeActivity() {
        application.removeActivity_(oContext);// 调用BaseApplication的销毁单个Activity方法
    }

    //销毁所有Activity方法
    public void removeALLActivity() {
        application.removeALLActivity_();// 调用BaseApplication的销毁所有Activity方法
    }

    /* 把Toast定义成一个方法  可以重复使用，使用时只需要传入需要提示的内容即可*/
    public void show_Toast(String text) {
        Toast.makeText(oContext, text, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object myEvent) {

    }

    public boolean isEmpty(String str) {
        if (!TextUtils.isEmpty(str) && !str.equals("null")) {
            return false;
        } else {
            return true;
        }
    }

    //等待上传的加载框
    public void dialogShow() {
        LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = mInflater.inflate(R.layout.dialog_common, null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(oContext, R.style.loading_dialog_style).setView(convertView);
        alertDialog = dialog.create();
        alertDialog.show();
    }

    public void dialogDismiss() {
        if (alertDialog != null) alertDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity();
        EventBus.getDefault().unregister(this);
    }

}