package com.lx.foodxing.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.lx.foodxing.MyApplication;
import com.lx.foodxing.R;
import com.lx.foodxing.base.BaseActivity;
import com.lx.foodxing.utils.Constant;
import com.lx.foodxing.utils.PrefUtils;

public class FilterActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {


    private TextView tv_app_title;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_filter;
    }

    @Override
    protected void initView() {
        tv_app_title = findViewById(R.id.tv_app_title);
        tv_app_title.setText("美食guolv");

        Switch sw_glutenfree = findViewById(R.id.sw_glutenfree);   //五谷蛋白
        Switch sw_lactosefree = findViewById(R.id.sw_lactosefree);  //不含乳糖
        Switch sw_vegetarian = findViewById(R.id.sw_vegetarian);  //素食主义者
        Switch sw_vegan = findViewById(R.id.sw_vegan);       //严格的素食主义者


        sw_glutenfree.setOnCheckedChangeListener(this);
        sw_lactosefree.setOnCheckedChangeListener(this);
        sw_vegetarian.setOnCheckedChangeListener(this);
        sw_vegan.setOnCheckedChangeListener(this);


        //初始化Switch值
        boolean isGlutenfree = PrefUtils.getInstance(MyApplication.getInstance()).getBoolean(Constant.spkey_glutenfree, false);
        boolean isLactosefree = PrefUtils.getInstance(MyApplication.getInstance()).getBoolean(Constant.spkey_lactosefree, false);
        boolean isVegetarian = PrefUtils.getInstance(MyApplication.getInstance()).getBoolean(Constant.spkey_vegetarian, false);
        boolean isVegan = PrefUtils.getInstance(MyApplication.getInstance()).getBoolean(Constant.spkey_vegan, false);
        sw_glutenfree.setChecked(isGlutenfree);
        sw_lactosefree.setChecked(isLactosefree);
        sw_vegetarian.setChecked(isVegetarian);
        sw_vegan.setChecked(isVegan);
    }

    @Override
    protected void initListener() {
        tv_app_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.sw_glutenfree:   //五谷蛋白
                PrefUtils.getInstance(MyApplication.getInstance()).put(Constant.spkey_glutenfree, isChecked);      //存储在SP里
                break;
            case R.id.sw_lactosefree: //不含乳糖
                PrefUtils.getInstance(MyApplication.getInstance()).put(Constant.spkey_lactosefree, isChecked);      //存储在SP里
                break;
            case R.id.sw_vegetarian:  //素食主义者
                PrefUtils.getInstance(MyApplication.getInstance()).put(Constant.spkey_vegetarian, isChecked);      //存储在SP里
                break;
            case R.id.sw_vegan:       //严格的素食主义者
                PrefUtils.getInstance(MyApplication.getInstance()).put(Constant.spkey_vegan, isChecked);      //存储在SP里
                break;
        }
    }
}