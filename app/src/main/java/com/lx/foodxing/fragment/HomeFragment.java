package com.lx.foodxing.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.foodxing.R;
import com.lx.foodxing.adapter.HomeAdapter;
import com.lx.foodxing.base.BaseFragment;
import com.lx.foodxing.bean.CategoryBean;
import com.lx.foodxing.ui.CategoryActivity;
import com.lx.foodxing.utils.Constant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;

public class HomeFragment extends BaseFragment {

    private HomeAdapter homeAdapter;
    private SwipeRefreshLayout srl_home;
    private RecyclerView mRecyclerView;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        //列表控件
        homeAdapter = new HomeAdapter(null);
        mRecyclerView = view.findViewById(R.id.rv_home);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(homeAdapter);
        homeAdapter.setAnimationEnable(true);

        initRefreshLayout();
        initLoadMore();
    }

    @Override
    protected void initData() {
        getHomeNetData(true);
    }

    @Override
    protected void initListener() {
        //条目点击事件的监听
        homeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                CategoryBean mCategoryBean = homeAdapter.getData().get(position);
                Intent intent=new Intent(getActivity(), CategoryActivity.class);
                intent.putExtra("cateId",mCategoryBean.getId());
                intent.putExtra("cateTitle",mCategoryBean.getTitle());
                startActivity(intent);
            }
        });
    }

    private void initRefreshLayout() {
        //刷新控件
        srl_home = view.findViewById(R.id.srl_home);
        srl_home.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //网络请求，请求完成，设置setRefreshing 为false
                getHomeNetData(true);
            }
        });
    }

    private void initLoadMore() {
        // 在 Application 中配置全局自定义的 LoadMoreView
//        LoadMoreModuleConfig.setDefLoadMoreView(new CustomLoadMoreView());
        //在当前页面配置全局自定义的 LoadMoreView
//        homeAdapter.getLoadMoreModule().setLoadMoreView(new CustomLoadMoreView());
        homeAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getHomeNetData(false);
            }
        });
        homeAdapter.getLoadMoreModule().setAutoLoadMore(true);
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        homeAdapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
    }

    //网络请求
    private void getHomeNetData(boolean isRefresh) {
        OkHttpUtils
                .get()
                .url(Constant.Url_home)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "请求失败", Toast.LENGTH_SHORT).show();

                        //记的请求结束关闭刷新
                        srl_home.setRefreshing(false);
                        //服务器错误，显示错误界面
                        homeAdapter.setEmptyView(getErrorView());
                    }

                    @Override
                    public void onResponse(String response, int ids) {
//                        Log.e("liuxing", "首页数据：" + response);

                        //记的请求结束关闭刷新
                        srl_home.setRefreshing(false);
                        //支持加载更多
                        homeAdapter.getLoadMoreModule().setEnableLoadMore(true);

                        /**
                         * 这里我解析数据不一样，是因为每个接口都有自己对应的数据结构，灵活去变化，解析出来就行
                         * 解析数据分两种，一种用原生的JSONObject去解析取值；  一种是gson框架解析
                         * 这里我用了两种，先用的原生的，取出category数据，然后用gson框架去解析
                         *
                         */
                        JSONObject jsonObject = null;
                        try {
                            //解析数据
                            jsonObject = new JSONObject(response);
                            String category = jsonObject.getString("category");
                            List<CategoryBean> newData = new Gson().fromJson(category, new TypeToken<List<CategoryBean>>() {
                            }.getType());
                            Log.e("liuxing", "新数据==" + newData.size());

                            //如果数据有code,判断code==200,如果不是200，就是 数据错误，显示错误界面
                            initViewData(isRefresh, newData);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
    }

    //处理数据和界面的关系
    private void initViewData(boolean isRefresh, List<CategoryBean> newData) {
        if (isRefresh) {     //第一次或者刷新需要先清空数据，重新添加
            if (newData != null && newData.size() > 0) {
                homeAdapter.setList(newData);
            } else {
                homeAdapter.setEmptyView(getEmptyDataView());  //数据为空，显示空界面
            }
        } else {     //加载更多，直接在原有的数据集合基础上，添加新数据
            if (newData != null && newData.size() > 0) {
                homeAdapter.addData(newData);
                homeAdapter.getLoadMoreModule().loadMoreComplete();    //已经得到数据，加载更多执行完成
            } else {
                //当后台加载更多无数据返回的时候，停止加载更多
                homeAdapter.getLoadMoreModule().loadMoreEnd();
            }
        }
    }


    private View getEmptyDataView() {
        View notDataView = getLayoutInflater().inflate(R.layout.empty_view, mRecyclerView, false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHomeNetData(true);       //点击重新请求数据
            }
        });
        return notDataView;
    }

    private View getErrorView() {
        View errorView = getLayoutInflater().inflate(R.layout.error_view, mRecyclerView, false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHomeNetData(true);      //点击重新请求数据
            }
        });
        return errorView;
    }

}
