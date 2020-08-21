package com.lx.foodxing.bean;

import java.io.Serializable;
import java.util.List;

public class FoodBean implements Serializable {


    /**
     * id : m1
     * categories : ["c1","c2"]
     * title : 番茄酱意大利面
     * affordability : 0
     * complexity : 0
     * imageUrl : https://upload.wikimedia.org/wikipedia/commons/thumb/2/20/Spaghetti_Bolognese_mit_Parmesan_oder_Grana_Padano.jpg/800px-Spaghetti_Bolognese_mit_Parmesan_oder_Grana_Padano.jpg
     * duration : 20
     * ingredients : ["4个西红柿","一汤匙橄榄油","1个洋葱","250克意大利面","香料","奶酪（可选）"]
     * steps : ["把西红柿和洋葱切成小块。","烧开水煮沸后加盐。","把意大利面放进开水里，大约10到12分钟就可以做好。","同时加热一些橄榄油，加入切好的洋葱。","两分钟后，加入番茄片、盐、胡椒和其他香料。","一旦意大利面做好了，调味汁就会做好。","在成品盘上随意加些奶酪。"]
     * isGlutenFree : false   //五谷蛋白
     * isVegan : true       //严格的素食主义者
     * isVegetarian : true   //素食主义者
     * isLactoseFree : true //不含乳糖
     */

    private String id;
    private String title;
    private int affordability;
    private int complexity;
    private String imageUrl;
    private int duration;
    private boolean isGlutenFree;   //五谷蛋白
    private boolean isVegan;    //严格的素食主义者
    private boolean isVegetarian; //素食主义者
    private boolean isLactoseFree; //不含乳糖
    private List<String> categories;
    private List<String> ingredients;
    private List<String> steps;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAffordability() {
        return affordability;
    }

    public void setAffordability(int affordability) {
        this.affordability = affordability;
    }

    public int getComplexity() {
        return complexity;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isIsGlutenFree() {
        return isGlutenFree;
    }

    public void setIsGlutenFree(boolean isGlutenFree) {
        this.isGlutenFree = isGlutenFree;
    }

    public boolean isIsVegan() {
        return isVegan;
    }

    public void setIsVegan(boolean isVegan) {
        this.isVegan = isVegan;
    }

    public boolean isIsVegetarian() {
        return isVegetarian;
    }

    public void setIsVegetarian(boolean isVegetarian) {
        this.isVegetarian = isVegetarian;
    }

    public boolean isIsLactoseFree() {
        return isLactoseFree;
    }

    public void setIsLactoseFree(boolean isLactoseFree) {
        this.isLactoseFree = isLactoseFree;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "DeatilsBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", affordability=" + affordability +
                ", complexity=" + complexity +
                ", imageUrl='" + imageUrl + '\'' +
                ", duration=" + duration +
                ", isGlutenFree=" + isGlutenFree +
                ", isVegan=" + isVegan +
                ", isVegetarian=" + isVegetarian +
                ", isLactoseFree=" + isLactoseFree +
                ", categories=" + categories +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                '}';
    }
}
