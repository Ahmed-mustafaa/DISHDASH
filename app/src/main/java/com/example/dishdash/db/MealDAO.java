package com.example.dishdash.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dishdash.model.Meal;


import java.util.List;

@Dao
public interface MealDAO {
    @Query("SELECT * FROM meals")
    LiveData<List<Meal>> getAllProducts();
    @Query("UPDATE meals SET isFavorite = :isFav WHERE idMeal = :mealId")
    void updateFavoriteStatus(String mealId, boolean isFav);

   @Query("SELECT * FROM meals WHERE isFavorite = 1")
    LiveData<List<Meal>> getFavoriteMeals();
    @Query("SELECT * FROM meals WHERE isFavorite = 1 AND userId = :userId")
    LiveData<List<Meal>> getFavoritesByUserId(String userId);
    @Query("DELETE FROM meals WHERE userId = :userId")
    void deleteMealsByUserId(String userId);
    @Insert
    void insertMeal(Meal meal);
@Delete
    void deleteMeal(Meal product);
}
