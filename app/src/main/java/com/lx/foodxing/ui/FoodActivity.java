package com.lx.foodxing.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lx.foodxing.MyApplication;
import com.lx.foodxing.R;
import com.lx.foodxing.adapter.CategoryAdapter;
import com.lx.foodxing.adapter.HomeBannerAdapter;
import com.lx.foodxing.adapter.MateAdapter;
import com.lx.foodxing.adapter.StepAdapter;
import com.lx.foodxing.base.BaseActivity;
import com.lx.foodxing.bean.FoodBean;
import com.lx.foodxing.utils.FoodUtils;
import com.lx.foodxing.utils.ScrollLinearLayoutManager;
import com.lx.foodxing.utils.ToastUtils;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FoodActivity extends BaseActivity implements View.OnClickListener {

    ArrayList<String> bannerList ;
    private TextView tv_app_title;
    private HomeBannerAdapter bannerAdapter;
    private FoodBean mFoodBean;
    private ImageView iv_food_collet;
    private boolean isCollect;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_food;
    }

    @Override
    protected void initView() {
        //上级界面传递过来整个菜品的数据
        mFoodBean = (FoodBean) getIntent().getSerializableExtra("mFoodBean");

        tv_app_title = findViewById(R.id.tv_app_title);
        tv_app_title.setText(mFoodBean.getTitle());
        iv_food_collet = findViewById(R.id.iv_food_collet);
        tv_app_title.setOnClickListener(this);
        iv_food_collet.setOnClickListener(this);
        //初始化收藏图标状态
        isCollect = FoodUtils.isCollectFoodOne(mFoodBean);
        setCollectState();


        //轮播图控件
        Banner banner_home = findViewById(R.id.banner_home);
        bannerAdapter = new HomeBannerAdapter(this, null);
        //设置轮播图底部小圆点
        banner_home.setIndicator(new CircleIndicator(this));
        banner_home.setAdapter(bannerAdapter);

        //制作材料
        MateAdapter  mateAdapter = new MateAdapter(null);
        RecyclerView mRecyclerView1 = findViewById(R.id.rv_food_mate);
        mRecyclerView1.setLayoutManager(new ScrollLinearLayoutManager(this));
        mRecyclerView1.setAdapter(mateAdapter);
        mateAdapter.setList(mFoodBean.getIngredients());

        //制作材料
        StepAdapter  stepAdapter = new StepAdapter(null);
        RecyclerView mRecyclerView2 = findViewById(R.id.rv_food_step);
        mRecyclerView2.setLayoutManager(new ScrollLinearLayoutManager(this));
        mRecyclerView2.setAdapter(stepAdapter);
        //添加条目间横线
        mRecyclerView2.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        stepAdapter.setList(mFoodBean.getSteps());
    }

    private void setCollectState() {
        if (isCollect){
            iv_food_collet.setImageResource(R.mipmap.collect_select);
        }else{
            iv_food_collet.setImageResource(R.mipmap.collect_nomal);
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        initBannerData();       //假设从后台获取数据再复制给控件，轮播图数据
        bannerAdapter.setDatas(bannerList);
        //不刷新，小圆点不出来，框架的原因
        bannerAdapter.notifyDataSetChanged();
    }

    //轮播图数据
    private void initBannerData() {
        bannerList = new ArrayList<String>();
        //故意模拟轮播图数据，实际情况会是后台给的一个图片集合
        bannerList.add(mFoodBean.getImageUrl());
        bannerList.add(mFoodBean.getImageUrl());
        bannerList.add(mFoodBean.getImageUrl());
        bannerList.add(mFoodBean.getImageUrl());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_app_title:
                finish();
                break;
            case R.id.iv_food_collet:
                isCollect=!isCollect;
                setCollectState();
                FoodUtils.collectFoodOne(mFoodBean);
                if (isCollect){
                    ToastUtils.showToast(this,"收藏成功");
                }else{
                    ToastUtils.showToast(this,"取消收藏");
                }
                //本页面操作后，记的通知上一个列表页，它对应的收藏图标也要发生相应的变化
                MyApplication.isFreshCategoryCollet=true;
                break;
            default:
                break;
        }
    }
}