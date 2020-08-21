package com.lx.foodxing.adapter;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lx.foodxing.R;
import com.lx.foodxing.bean.CategoryBean;
import com.lx.foodxing.bean.FoodBean;
import com.lx.foodxing.utils.FoodUtils;
import com.lx.foodxing.utils.MyUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 主页面--左侧菜单的列表布局--Adapter
 */
public class MateAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public MateAdapter(List<String> data) {

        super(R.layout.item_food_mate);

    }


    @Override
    protected void convert(@NotNull BaseViewHolder helper, @NotNull String item) {
        int position = helper.getLayoutPosition();
        helper.setText(R.id.tv_item_mate_name, item);

    }




}
