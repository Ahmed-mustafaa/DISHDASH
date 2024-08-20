package com.example.dishdash.NetworkCall;

import com.example.dishdash.model.Category;
import com.example.dishdash.model.Country;
import com.example.dishdash.model.Meal;

import java.util.List;

public class MealResponse {
    private List<Meal> meals;
    private List<Country> countries;
    private List<Category> categories;

    public List<Meal> getMeals() {
        return meals;
    }



    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public List<Category> getCategories() {
        return categories;
    }



}
