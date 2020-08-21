package com.lx.foodxing.utils;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lx.foodxing.MyApplication;
import com.lx.foodxing.bean.FoodBean;

import java.util.ArrayList;
import java.util.List;

public class FoodUtils {

    //通过分类Id，判断筛选出该类下的菜品列表
    public static List<FoodBean> getFoodListById(List<FoodBean> totalData, String cateId) {
        List<FoodBean> newData = new ArrayList<FoodBean>();
        for (int i = 0; i < totalData.size(); i++) {
            FoodBean mFoodBean = totalData.get(i);
            if (mFoodBean.getCategories().contains(cateId)) {
                newData.add(mFoodBean);
            }
        }
        return newData;
    }

    //收藏或者取消收藏的菜品
    public static void collectFoodOne(FoodBean mFoodBean) {
        boolean isCollect = false;        //是否收藏
        int index = 0;        //标记收藏数据item的位置
        List<FoodBean> list = getFoodCache();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                FoodBean item = list.get(i);
                if (TextUtils.equals(item.getId(), mFoodBean.getId())) {
                    isCollect = true;
                    index = i;
                    break;
                }
            }
        }
        if (isCollect) {
            Log.e("liuxing", "去删除");
            list.remove(index);  //取消即删除该菜品
        } else {
            Log.e("liuxing", "去添加");
            list.add(mFoodBean);  //添加即收藏该菜品
        }
        //存起来
        setFoodCache(list);
    }

    //是否收藏了该菜品
    public static boolean isCollectFoodOne(FoodBean mFoodBean) {
        boolean isCollect = false;
        List<FoodBean> list = getFoodCache();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                FoodBean item = list.get(i);
                if (TextUtils.equals(item.getId(), mFoodBean.getId())) {
                    isCollect = true;
                    break;
                }
            }
        }
        return isCollect;
    }


    //取出缓存的收藏数据
    public static List<FoodBean> getFoodCache() {
        List<FoodBean> collectData = new ArrayList<FoodBean>();
        ;
        //取出SP的Json字符串
        String colletJson = PrefUtils.getInstance(MyApplication.getInstance()).getString(Constant.spkey_collect, "");
//        Log.e("liuxing","取出字符串"+colletJson.toString());
        if (!TextUtils.isEmpty(colletJson)) {
            //Json字符串转化成集合
            collectData = new Gson().fromJson(colletJson, new TypeToken<List<FoodBean>>() {
            }.getType());
        }
        Log.e("liuxing", "取值数据--" + collectData.size());
        return collectData;
    }

    //进行缓存收藏数据
    public static void setFoodCache(List<FoodBean> data) {
        //集合转化成
        String colletJson = new Gson().toJson(data);
        //保存Json字符串
        PrefUtils.getInstance(MyApplication.getInstance()).put(Constant.spkey_collect, colletJson);      //存储在SP里
    }


    //过滤菜品数据
    public static List<FoodBean> getFoodListByFilter(List<FoodBean> totalData) {
        boolean isGlutenfree = PrefUtils.getInstance(MyApplication.getInstance()).getBoolean(Constant.spkey_glutenfree, false);
        boolean isLactosefree = PrefUtils.getInstance(MyApplication.getInstance()).getBoolean(Constant.spkey_lactosefree, false);
        boolean isVegetarian = PrefUtils.getInstance(MyApplication.getInstance()).getBoolean(Constant.spkey_vegetarian, false);
        boolean isVegan = PrefUtils.getInstance(MyApplication.getInstance()).getBoolean(Constant.spkey_vegan, false);
        List<FoodBean> newData = new ArrayList<FoodBean>();
        for (int i = 0; i < totalData.size(); i++) {
            FoodBean mFoodBean = totalData.get(i);
            if (mFoodBean.isIsGlutenFree() && isGlutenfree) {
                //不添加
            } else if (mFoodBean.isIsLactoseFree() && isLactosefree) {
                //不添加
            } else if (mFoodBean.isIsVegetarian() && isVegetarian) {
                //不添加
            } else if (mFoodBean.isIsVegan() && isVegan) {
                //不添加
            }else{
                newData.add(mFoodBean);
            }
        }
        return newData;
    }
}
