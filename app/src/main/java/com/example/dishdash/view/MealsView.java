package com.example.dishdash.view;

import com.example.dishdash.model.Meal;

import java.util.List;

public interface MealsView {
     void showRandomMeal(Meal meal);
     void showDailyMeals(List<Meal> meals);
     void showError(String message);
}
