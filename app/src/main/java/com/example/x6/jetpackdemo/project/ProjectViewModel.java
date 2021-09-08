package com.example.x6.jetpackdemo.project;

import androidx.lifecycle.ViewModel;

//整个项目使用的ViewModel
public class ProjectViewModel extends ViewModel {

    private final CustomProjectLiveData<Boolean> isActive;  //App是否存活
    private final CustomProjectLiveData<Boolean> isLogin; //App是否登录

    public CustomProjectLiveData<Boolean> getIsActive() {
        return isActive;
    }

    public CustomProjectLiveData<Boolean> getIsLogin() {
        return isLogin;
    }
}
