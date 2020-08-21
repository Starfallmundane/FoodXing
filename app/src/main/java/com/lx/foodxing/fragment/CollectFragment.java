package com.lx.foodxing.fragment;

import android.view.View;

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
import com.lx.foodxing.utils.FoodUtils;

import java.util.List;

public class CollectFragment extends BaseFragment {

    private CategoryAdapter colletAdapter;
    private SwipeRefreshLayout srl_collet;
    private RecyclerView mRecyclerView;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_collect;
    }

    @Override
    protected void initView() {
        //列表控件
        colletAdapter = new CategoryAdapter(null);
        mRecyclerView = view.findViewById(R.id.rv_collect);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(colletAdapter);
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
                    FoodUtils.collectFoodOne(mFoodBean);
                    //收藏列表需要删除该条数据
                    colletAdapter.removeAt(position);
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
    }
}
