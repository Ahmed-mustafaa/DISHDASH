package com.example.dishdash.model.Repository;

import com.example.dishdash.model.Meal;

import java.util.List;

public class FavResponse {
        private List<Meal> meals;

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> products) {
        this.meals = meals;
    }

    public FavResponse(List<Meal> meals ){
    this.meals = meals;

}

    }