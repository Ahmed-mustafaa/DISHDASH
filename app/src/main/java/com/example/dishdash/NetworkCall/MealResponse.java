package com.example.dishdash.NetworkCall;

import com.example.dishdash.model.Meal;

import java.util.List;

public class MealResponse {
    private List<Meal> meals;

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public MealResponse(List<Meal> meals) {
        this.meals = meals;

    }
}
