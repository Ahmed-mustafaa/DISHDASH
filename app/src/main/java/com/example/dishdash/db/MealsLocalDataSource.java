package com.example.dishdash.db;

import androidx.lifecycle.LiveData;

import com.example.dishdash.model.Meal;


import java.util.List;

public interface MealsLocalDataSource {
      void insertMeal(Meal meal);
      void deleteMeal(Meal meal);
       LiveData<List<Meal>>getSortedMeals();
       MealDAO mealDAO();
    FavDAO favDAO();
    void updateMeal(Meal meal);
     LiveData<List<Meal>> getFavoritesByUserId(String userId) ;


    }
