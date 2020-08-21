package com.lx.foodxing.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

public class MyUtils {

    public static void setTextImage(Context mContext, TextView textView, int pic, int position) {
        Drawable img = mContext.getResources().getDrawable(pic);
        // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
        if (position == 1) {
            textView.setCompoundDrawables(img, null, null, null); //设置左图标
        } else if (position == 2) {
            textView.setCompoundDrawables(null, img, null, null); //设置上图标
        } else if (position == 3) {
            textView.setCompoundDrawables(null, null, img, null); //设置右图标
        } else if (position == 4) {
            textView.setCompoundDrawables(null, null, null, img); //设置下图标
        }
    }
}
