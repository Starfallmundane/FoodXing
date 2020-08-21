package com.lx.foodxing.utils;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Created by Administrator on 2017/8/2.
 */

public class ScrollLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = false;
    public ScrollLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ScrollLinearLayoutManager(Context context) {
        super(context);
    }

    public ScrollLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation,reverseLayout );
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }


}
