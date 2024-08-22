package com.example.dishdash.model;

import androidx.room.Dao;
import androidx.room.Insert;

import java.util.List;
import androidx.room.Query;


@Dao
public interface IngredientDAO {
    @Insert
    void insert(Ingredient ingredient);

    @Insert
    void insertAll(List<Ingredient> ingredients);
    @Query("SELECT * FROM ingredients WHERE mealId = :mealId")

    List<Ingredient> getIngredientsForMeal(int mealId);
}
