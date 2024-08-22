package com.example.dishdash.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dishdash.model.Ingredient;
import com.example.dishdash.model.IngredientDAO;
import com.example.dishdash.model.Meal;

@Database(entities = {Meal.class, Ingredient.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    static volatile  AppDataBase instance = null;
    public abstract MealDAO getProductDAO();
    public abstract IngredientDAO IngrdientDAO();

    public static synchronized AppDataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class,"MealsDB").fallbackToDestructiveMigration().build();

        }
        return instance;
    }
}
