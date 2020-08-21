package com.lx.foodxing.adapter;

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
    public HomeAdapter(List<CategoryBean> data) {
        /**
         *  布局传递
         *  *****
         *  这里一定要说明一点
         *  列表竖直方向时，item_left_menu最外层布局LinearLayout，高度必须用 android:layout_height="wrap_content"
         *  列表竖横方向时，item_left_menu最外层布局LinearLayout，宽度必须用 android:layout_height="layout_width"
         *  这种情况是为了防止一个条目就把屏幕占满了只能显示一条的情况
         *
         *  以后所有列表都是这样情况设置的
         */
        super(R.layout.item_home_category);
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
