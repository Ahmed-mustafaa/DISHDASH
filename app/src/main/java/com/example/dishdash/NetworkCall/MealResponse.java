package com.example.dishdash.NetworkCall;

import com.example.dishdash.model.Country;
import com.example.dishdash.model.Meal;

import java.util.List;

public class MealResponse {
    private List<Meal> meals;
    private List<Country> countries;

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public MealResponse(List<Meal> meals , List<Country> countries) {
        this.meals = meals;
this.countries= countries;
    }



}
