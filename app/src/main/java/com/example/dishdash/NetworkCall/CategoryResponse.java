package com.example.dishdash.NetworkCall;

import com.example.dishdash.model.Category;
import com.example.dishdash.model.Meal;

import java.util.List;

public class CategoryResponse {
    private List<Meal> meals;

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

}
