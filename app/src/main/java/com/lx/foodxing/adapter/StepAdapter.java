package com.lx.foodxing.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lx.foodxing.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 主页面--左侧菜单的列表布局--Adapter
 */
public class StepAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {


    public StepAdapter(List<String> data) {
        super(R.layout.item_food_step);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder helper, @NotNull String item) {
        int position = helper.getLayoutPosition();
        helper.setText(R.id.tv_item_step_number, "#"+(position+1));
        helper.setText(R.id.tv_item_step_name, item);
    }

}
