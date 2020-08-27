package com.lx.foodxing;

import android.app.Application;

import com.chad.library.adapter.base.module.LoadMoreModuleConfig;
import com.lx.foodxing.view.CustomLoadMoreView;

public class MyApplication extends Application {

    private static MyApplication instance;

    public static boolean isFreshCollet=false;
    public static boolean isFreshCategoryCollet=false;


    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        LoadMoreModuleConfig.setDefLoadMoreView(new CustomLoadMoreView());
    }

    /**
     * 获得实例
     *
     * @return
     */
    public static MyApplication getInstance() {
        return instance;
    }
}
