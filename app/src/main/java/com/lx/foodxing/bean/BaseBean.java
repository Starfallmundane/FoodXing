package com.lx.foodxing.bean;

import android.text.TextUtils;

public class BaseBean<T> {

    //以前的数据
    public String code;//状态码
    public String message;//状态码
    public T data;

    public boolean isSuccess(){
        return TextUtils.equals(code,"0");
    }

}
