package com.example.dishdash.db;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.dishdash.model.Meal;

import java.util.List;

@Dao
public interface FavDAO {


    @Query("SELECT * FROM meals WHERE isFavorite = 1 AND userId = :userId")
    LiveData<List<Meal>> getFavoritesByUserId(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(Meal meal);

    @Update
    void updateMeal(Meal meal);

    @Delete
    void deleteMeal(Meal meal);

    @Query("DELETE FROM meals WHERE idMeal = :mealId AND userId = :userId")
    void deleteMealById(String mealId, String userId);

    @Query("DELETE FROM meals")
    void clearAllFavorites();

    @Transaction
    default void clearAndInsertMeals(List<Meal> meals) {
        clearAllFavorites();
        insertMeals(meals);
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeals(List<Meal> meals);

}
