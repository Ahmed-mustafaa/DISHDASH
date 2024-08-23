package com.example.dishdash.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.dishdash.model.Ingredient;
import com.example.dishdash.model.IngredientDAO;
import com.example.dishdash.model.Meal;

@Database(entities = {Meal.class, Ingredient.class}, version = 2, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    static volatile  AppDataBase instance = null;
    public abstract MealDAO getProductDAO();
    public abstract IngredientDAO IngrdientDAO();
    public abstract FavDAO favoriteDao();


    public static synchronized AppDataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDataBase.class,"MealsDB").fallbackToDestructiveMigration().build();

        }
        return instance;
    }
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Example of a migration that adds a new column
            database.execSQL("ALTER TABLE meals ADD COLUMN userId TEXT");
        }
    };
}
