package com.example.dishdash.view;

import com.example.dishdash.model.Category;
import com.example.dishdash.model.Country;
import com.example.dishdash.model.Meal;

import java.util.List;

public interface MealsView {
     void showRandomMeal(Meal meal);
     void showDailyMeals(List<Meal> meals);
    void showFABOptions();
    void hideFABOptions();
     void showAllCountries(List<Country> countries);
     void showError(String message);
    void showAllCategories(List<Category> categories);
}
