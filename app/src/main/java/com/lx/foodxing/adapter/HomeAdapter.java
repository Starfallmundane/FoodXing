package com.lx.foodxing.adapter;

import android.content.Context;
import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lx.foodxing.R;
import com.lx.foodxing.bean.CategoryBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 主页面--左侧菜单的列表布局--Adapter
 */
public class HomeAdapter extends BaseQuickAdapter<CategoryBean, BaseViewHolder> implements LoadMoreModule {

    /**
     * 1. List<String>  字符串集合时这样写
     * 2. List<XxxBean>  对象集合时这样写，导致这样下面item 的类型就得改成XxxBean，可以直接用属性了
     *
     * @param data
     */

    private Context mContext;
    public HomeAdapter(List<CategoryBean> data, Context mContext) {
        super(R.layout.item_home_category);
        this. mContext=mContext;
    }


    @Override
    protected void convert(@NotNull BaseViewHolder helper, @NotNull CategoryBean item) {
        int position = helper.getLayoutPosition();
//        Log.e("liuxing",position+"=========="+item.getTitle());
        /**
         * BaseQuickAdapter  基本用法参考文档
         * https://github.com/CymChad/BaseRecyclerViewAdapterHelper/blob/master/readme/1-BaseQuickAdapter.md
         *
         */

        helper.setText(R.id.tv_item_homecate_name, position + "." + item.getTitle());
        int colorBg= Color.parseColor("#"+item.getColor());
        helper.setBackgroundColor(R.id.ll_item_homecate_all, colorBg);

    }
}
