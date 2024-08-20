package com.example.dishdash.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {
    private String strCategory;
    private List<Meal> categoryMeals;
    @SerializedName("strCategoryThumb")
    public String strCategoryThumb;
    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public List<Meal> getCategoryMeals() {
        return categoryMeals;
    }

    public void setCategoryMeals(List<Meal> categoryMeals) {
        this.categoryMeals = categoryMeals;
    }

    public String getStrCategoryThumb() {
        return strCategoryThumb;
    }

    public void setStrCategoryThumb(String strCategoryThumb) {
        this.strCategoryThumb = strCategoryThumb;
    }

    public  Category(String strCategory) {
        this.strCategory = strCategory;
    }
}
