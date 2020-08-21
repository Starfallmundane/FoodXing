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
public class CategoryAdapter extends BaseQuickAdapter<FoodBean, BaseViewHolder> implements LoadMoreModule {


    public CategoryAdapter(List<CategoryBean> data) {

        super(R.layout.item_category_meal);

        //注册收藏按钮点击事件
        addChildClickViewIds(R.id.tv_item_catemeal_collect);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder helper, @NotNull FoodBean item) {
        int position = helper.getLayoutPosition();
        helper.setText(R.id.tv_item_catemeal_name, item.getTitle());
        helper.setText(R.id.tv_item_catemeal_time, item.getDuration() + "分钟");
        helper.setText(R.id.tv_item_catemeal_hard, getHardString(item.getComplexity()));
        ImageView iv_item_catemeal_pic = helper.getView(R.id.iv_item_catemeal_pic);
        Glide.with(this.getContext())
                .load(item.getImageUrl())
                .into(iv_item_catemeal_pic);



        TextView tv_item_catemeal_collect = helper.getView(R.id.tv_item_catemeal_collect);
        //判断是否收藏
        boolean isCollect = FoodUtils.isCollectFoodOne(item);
        Log.e("liuxing","是否收藏--"+isCollect);
        if (isCollect) {
            tv_item_catemeal_collect.setText("收藏");
            MyUtils.setTextImage(this.getContext(), tv_item_catemeal_collect, R.mipmap.collect_select, 1);
        } else {
            tv_item_catemeal_collect.setText("未收藏");
            MyUtils.setTextImage(this.getContext(), tv_item_catemeal_collect, R.mipmap.collect_nomal, 1);
        }

    }

    private String getHardString(int complexity) {
        String hardString;
        switch (complexity) {
            case 0:
                hardString = "简单";
                break;
            case 1:
                hardString = "中度";
                break;
            case 2:
                hardString = "复杂";
                break;
            default:
                hardString = "简单";
                break;

        }
        return hardString;
    }


}
