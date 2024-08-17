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

  /* @Query("SELECT * FROM meals WHERE isFavorite = 1")
    LiveData<List<Product>> getFavoriteProducts();
*/

    @Insert
    void insertProduct(Meal meal);
    @Delete
    void deleteProduct(Meal meal);
@Update
    void updateMeal(Meal meal);
}
