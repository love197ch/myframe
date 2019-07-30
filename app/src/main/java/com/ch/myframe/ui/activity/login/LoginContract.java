package com.ch.myframe.ui.activity.login;

import com.ch.myframe.base.BaseMvpCallback;
import com.ch.myframe.bean.User;

public interface LoginContract {
    interface View extends  BaseMvpCallback{
        void httpCallback(User user);
        void httpError(String e);
    }

    interface Presenter {
        void login(String username, String password);
    }

}
