package com.example.x6.jetpackdemo.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.x6.jetpackdemo.project.ProjectViewModel;

import java.util.List;

public class baseActivity extends AppCompatActivity {

    //贯穿项目唯一的一份projectViewModel
    protected ProjectViewModel projectViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        projectViewModel = getAppViewModelProvider().get(projectViewModel.class);

    }

    /**
     * 给此BaseActivity 得到ViewModelProvider
     * application
     * @return 唯一的一份ViewModelProvider 加载的唯一的ProjectViewModel
     */
    private ViewModelProvider getAppViewModelProvider() {
        return ((ProjectApplication)getApplicationContext()).getAppViewModelProvider(this);
    }
}
