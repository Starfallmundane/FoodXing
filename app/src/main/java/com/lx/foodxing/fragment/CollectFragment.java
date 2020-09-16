package com.lx.foodxing.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.lx.foodxing.MyApplication;
import com.lx.foodxing.R;
import com.lx.foodxing.adapter.CategoryAdapter;
import com.lx.foodxing.base.BaseFragment;
import com.lx.foodxing.bean.FoodBean;
import com.lx.foodxing.ui.NewActivity;
import com.lx.foodxing.utils.FoodUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;

public class CollectFragment extends BaseFragment {

    private CategoryAdapter colletAdapter;
    private SwipeRefreshLayout srl_collet;
    private RecyclerView mRecyclerView;
    public TextView tvResult;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_collect;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        //列表控件
        colletAdapter = new CategoryAdapter(null);
        TextView tv_new = view.findViewById(R.id.tv_new);
        tvResult = view.findViewById(R.id.tv_result);
        mRecyclerView = view.findViewById(R.id.rv_collect);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(colletAdapter);

        tv_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewActivity.class));
            }
        });
    }

    @Override
    protected void initData() {
        List<FoodBean> data = FoodUtils.getFoodCache();
        colletAdapter.setList(data);
    }

    @Override
    protected void initListener() {
        colletAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.tv_item_catemeal_collect) {
                    //操作该条数据，收藏或者取消收藏
                    FoodBean mFoodBean = colletAdapter.getData().get(position);
                    //缓存数据移除，必须保证代码正确性，否则下面代码页面移除也是个假象移除
                    FoodUtils.collectFoodOne(mFoodBean);
                    //页面移除，收藏列表需要删除该条数据
                    colletAdapter.removeAt(position);
                    //或者刷新数据，建议上面一种
                   // initData();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //获取焦点的时候，就得判断是否需要重新刷新加载数据，刚进APP是不用的，因为默认是false
        boolean isFresh = MyApplication.isFreshCollet;
        if (isFresh) {
            initData();
            //记的刷新后设置为false，取消标识
            MyApplication.isFreshCollet=false;
        }

        ((Activity)getActivity()).runOnUiThread(new Runnable() {
            public void run() {

            }
        });
    }


    /**
     * 回答问题成功时，需要检查该列表，如果存在相同问题，则移除该条数据
     */
    @Subscriber(tag = "mytag_communicate_changeanswer")
    private void updateDataWithTag(String message) {
        if (message != null) {
            Log.e("liuxing","我接收的数据"+message);

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
