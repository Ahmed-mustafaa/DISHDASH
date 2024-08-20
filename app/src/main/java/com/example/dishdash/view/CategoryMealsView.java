package com.example.dishdash.view;

import com.example.dishdash.model.Meal;

import java.util.List;

public interface CategoryMealsView {
    void showLoading();
    void hideLoading();
    void showMeals(List<Meal> meals);
    void showError(String message);

}
