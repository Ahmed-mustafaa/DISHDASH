package com.example.dishdash.db;

import androidx.lifecycle.LiveData;

import com.example.dishdash.model.Meal;


import java.util.List;

public interface ProductsLocalDataSource {
      void insertProducts(Meal meal);
      void deleteProducts(Meal meal);
       LiveData<List<Meal>>getSortedMeals();
       MealDAO mealDAO();
    void updateProduct(Meal meal);

}
