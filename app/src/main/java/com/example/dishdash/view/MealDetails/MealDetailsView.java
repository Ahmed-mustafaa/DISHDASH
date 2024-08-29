package com.example.dishdash.view.MealDetails;

import com.example.dishdash.model.Meal;

import java.util.List;
import java.util.Map;

public interface MealDetailsView {
    void showMealDetails (Meal meal);
    void showIngredients(List<Map<String, String>> ingredients);

    void showErrorMessage(String errorMessage);
}
