package com.example.dishdash.db;

import androidx.lifecycle.LiveData;

import com.example.dishdash.model.Meal;


import java.util.List;

public interface ProductsLocalDataSource {
      void insertMeal(Meal meal);
      void deleteMeal(Meal meal);
       LiveData<List<Meal>>getSortedMeals();
       MealDAO mealDAO();
    void updateMeal(Meal meal);

}
