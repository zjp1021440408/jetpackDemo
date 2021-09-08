package com.example.x6.jetpackdemo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

/**
 * 基于jetpack框架的demo
 * date 2021/9/8
 * @author nanchen
 *
 * 整个项目的Application
 * "implements ViewModelStoreOwner" 让整个项目持有一份被观察者对象，为了完成共享
 */
public class ProjectApplication extends Application implements ViewModelStoreOwner {

    private ViewModelStore mAppViewModelStore; //存放ViewModel
    private ViewModelProvider.Factory mFactory;

    @Override
    public void onCreate() {
        super.onCreate();

        //实例化 ViewModelStore 一次
        mAppViewModelStore = new ViewModelStore();

        //完成初始化工作
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return mAppViewModelStore;
    }
}
