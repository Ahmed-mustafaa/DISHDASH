package com.example.dishdash.view;

import com.example.dishdash.model.Meal;

public interface MealsView {
     void showRandomMeal(Meal meal);
     void showError(String message);
}
