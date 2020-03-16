package com.ch.myframe.dragger.module;

import android.app.Activity;
import androidx.fragment.app.Fragment;

import com.ch.myframe.dragger.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author Administrator
 */
@Module
public class FragmentModule {

    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return fragment.getActivity();
    }
}
