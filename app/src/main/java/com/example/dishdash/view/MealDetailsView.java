package com.example.dishdash.view;

import com.example.dishdash.model.Meal;

public interface MealDetailsView {
    void showMealDetails (Meal meal);
    void showErrorMessage(String errorMessage);
}
