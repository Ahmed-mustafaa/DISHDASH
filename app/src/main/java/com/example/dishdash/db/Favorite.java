package com.example.dishdash.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.dishdash.model.Meal;

@Entity(tableName = "favorites",
        foreignKeys = @ForeignKey(entity = Meal.class,
                parentColumns = "idMeal",
                childColumns = "mealId",
                onDelete = ForeignKey.CASCADE))
public class Favorite {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int userId; // Link to user
    public String mealId; // Link to meal
}