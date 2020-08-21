package com.lx.foodxing.base;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        initView();
        initData();
        initListener();
    }

    protected abstract int getLayoutResId();

    protected abstract void initView();

    protected void initData() {

    }

    protected void initListener() {

    }

    void showProgress() {
        progress = new ProgressDialog(this);
        progress.show();

    }

    void dissProgress() {
        if (progress != null) {
            progress.dismiss();
        }
    }


}