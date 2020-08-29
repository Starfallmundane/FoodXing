package com.lx.foodxing.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.foodxing.MyApplication;
import com.lx.foodxing.R;
import com.lx.foodxing.adapter.CategoryAdapter;
import com.lx.foodxing.base.BaseActivity;
import com.lx.foodxing.bean.FoodBean;
import com.lx.foodxing.utils.Constant;
import com.lx.foodxing.utils.FoodUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

import okhttp3.Call;

public class CategoryActivity extends BaseActivity {

    private CategoryAdapter cateAdapter;
    private SwipeRefreshLayout srl_category;
    private RecyclerView mRecyclerView;
    private String cateId;
    private String cateTitle;
    private TextView tv_app_title;
    private int saveStartPosition;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_category;
    }

    @Override
    protected void initData() {
        getCategoryNetData(true);
    }

    @Override
    protected void initView() {
        cateId = getIntent().getStringExtra("cateId");
        cateTitle = getIntent().getStringExtra("cateTitle");

        tv_app_title = findViewById(R.id.tv_app_title);
        tv_app_title.setText(cateTitle);
        //列表控件
        cateAdapter = new CategoryAdapter(null);
        mRecyclerView = findViewById(R.id.rv_category);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(cateAdapter);
        cateAdapter.setAnimationEnable(true);

        initRefreshLayout();
    }

    @Override
    protected void initListener() {
        tv_app_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //条目里某个按钮的点击事件
        cateAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.tv_item_catemeal_collect) {
                    //操作该条数据，收藏或者取消收藏
                    FoodBean mFoodBean = cateAdapter.getData().get(position);
                    FoodUtils.collectFoodOne(mFoodBean);
                    //刷新该条目，改变收藏状态图标
                    cateAdapter.notifyItemChanged(position);

                    //这里操作了就得告诉收藏列表，要刷新数据了
                    MyApplication.isFreshCollet = true;
                }
            }
        });

        //条目点击事件
        cateAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                saveStartPosition=position;     //做个标记
                FoodBean mFoodBean = cateAdapter.getData().get(position);
                Intent intent = new Intent();
                intent.setClass(CategoryActivity.this, FoodActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("mFoodBean", mFoodBean);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void initRefreshLayout() {
        //刷新控件
        srl_category = findViewById(R.id.srl_category);
        srl_category.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //网络请求，请求完成，设置setRefreshing 为false
                getCategoryNetData(true);
            }
        });
    }

    //网络请求
    private void getCategoryNetData(boolean isRefresh) {
        OkHttpUtils
                .get()
                .url(Constant.Url_cate)
                .addParams("id", cateId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                        Toast.makeText(CategoryActivity.this, "请求失败", Toast.LENGTH_SHORT).show();

                        //记的请求结束关闭刷新
                        srl_category.setRefreshing(false);
                        //服务器错误，显示错误界面
                        cateAdapter.setEmptyView(getErrorView());
                    }

                    @Override
                    public void onResponse(String response, int ids) {
                        Log.e("liuxing", "列表数据：" + response);

                        //记的请求结束关闭刷新
                        srl_category.setRefreshing(false);

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
                            String meal = jsonObject.getString("meal");
                            List<FoodBean> mealData = new Gson().fromJson(meal, new TypeToken<List<FoodBean>>() {
                            }.getType());
                            Log.e("liuxing", "新数据==" + mealData.size());

                            //如果数据有code,判断code==200,如果不是200，就是 数据错误，显示错误界面
                            /**
                             * 这里是后台全部返回了，所以需要自己过滤下，
                             * 数据对象里categories，包含当前类别ID的数据，就属于该类别下的数据
                             */
                            //从后台数据里筛选出该类下的菜品列表
                            List<FoodBean> newData1 = FoodUtils.getFoodListById(mealData, cateId);
                            //过滤不需要的菜品，从后台数据里筛选出不需要过滤的菜品列表
                            List<FoodBean> newData2 = FoodUtils.getFoodListByFilter(newData1);
                            cateAdapter.setList(newData2);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private View getEmptyDataView() {
        View notDataView = getLayoutInflater().inflate(R.layout.empty_view, mRecyclerView, false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCategoryNetData(true);       //点击重新请求数据
            }
        });
        return notDataView;
    }

    private View getErrorView() {
        View errorView = getLayoutInflater().inflate(R.layout.error_view, mRecyclerView, false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCategoryNetData(true);      //点击重新请求数据
            }
        });
        return errorView;
    }


    @Override
    protected void onResume() {
        super.onResume();
        boolean isFresh = MyApplication.isFreshCategoryCollet;
        if (isFresh) {
            //刷新跳转时，做标记的该条数据
            cateAdapter.notifyItemChanged(saveStartPosition);
            MyApplication.isFreshCategoryCollet = false;
        }
    }
}