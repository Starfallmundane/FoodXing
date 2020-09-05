package com.lx.foodxing;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lx.foodxing.adapter.MenuLeftAdapter;
import com.lx.foodxing.base.BaseActivity;
import com.lx.foodxing.event.MessageWrap;
import com.lx.foodxing.fragment.CollectFragment;
import com.lx.foodxing.fragment.HomeFragment;
import com.lx.foodxing.ui.FilterActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {


    private ArrayList<Fragment> fragments = new ArrayList();//fragment的集合
    private int lastShowFragment = 0;   //表示最后一个显示的Fragment
    private MenuLeftAdapter menuAdapter;
    private DrawerLayout dl_home;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        initViewBotton();     //初始化底部导航控件
        initFragments();//初始化fragment
        initMenu();//初始化侧拉菜单Menu
    }

    //菜单相关代码
    private void initMenu() {

        //准备数据，有可能是写死数据，有可能是网络获取数据，如下面备注说明
        ArrayList<String> menuList=new ArrayList<String>();
        menuList.add("进餐");
        menuList.add("过滤");

        /**
         * 这里创建Adapter填入数据又两种情况
         * 1.是本地就有数据，有固定的死数据，你可以先创建号数据这样直接传入  new MenuLeftAdapter(数据)
         *
         * 2.这种情况常用（可以包含上一种）
         * 大部分通过网络获取数据，就需要先创建一个空的数据Adapter，最后再去传入数据
         * 先  new MenuLeftAdapter(null)
         * 后  adapter.setNewData(数据);
         */
        dl_home = findViewById(R.id.dl_home);
        RecyclerView rv_menu = findViewById(R.id.rv_menu);
        menuAdapter = new MenuLeftAdapter(null);
        rv_menu.setLayoutManager(new LinearLayoutManager(this));
        rv_menu.setAdapter(menuAdapter);
        menuAdapter.setList(menuList);
    }

    public void initViewBotton() {
        RadioGroup mRadio = findViewById(R.id.main_radio);
        //点击RadioGroup按钮进行切换fragment
        mRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.rb_function:
                        if (lastShowFragment != 0) {
                            switchFrament(lastShowFragment, 0);
                            lastShowFragment = 0;
                        }
                        break;
                    case R.id.rb_news_center:
                        if (lastShowFragment != 1) {
                            switchFrament(lastShowFragment, 1);
                            lastShowFragment = 1;
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void switchFrament(int lastIndex, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments.get(lastIndex));
        if (!fragments.get(index).isAdded()) {
            transaction.add(R.id.framelayout_shou, fragments.get(index));
        }
        transaction.show(fragments.get(index)).commitAllowingStateLoss();
    }

    public void initFragments() {
        fragments.add(new HomeFragment());
        fragments.add(new CollectFragment());
        lastShowFragment = 0;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.framelayout_shou, (Fragment) fragments.get(0))
                .show(fragments.get(0))
                .commit();
    }

    @Override
    protected void initListener() {
        menuAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (position==0){
                    dl_home.closeDrawers();
                }else{
                    dl_home.closeDrawers();
                    startActivity(new Intent(MainActivity.this, FilterActivity.class));
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 注意一个大坑货
     * EventBus不能直接在Fragment里使用
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(MessageWrap message) {
        CollectFragment collectFragment= (CollectFragment) fragments.get(1);
        collectFragment.tvResult.setText(message.message);
    }
}