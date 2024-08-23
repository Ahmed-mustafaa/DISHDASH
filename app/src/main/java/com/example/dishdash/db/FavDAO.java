package com.example.dishdash.db;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.dishdash.model.Meal;

import java.util.List;

@Dao
public interface FavDAO {


    @Query("SELECT * FROM meals WHERE isFavorite = 1 AND userId = :userId")
    LiveData<List<Meal>> getFavoritesByUserId(String userId);

    @Insert
    void insertMeal(Meal meal);

    @Update
    void updateMeal(Meal meal);

    @Delete
    void deleteMeal(Meal meal);

    @Query("DELETE FROM meals WHERE idMeal = :mealId AND userId = :userId")
    void deleteMealById(String mealId, String userId);
}
