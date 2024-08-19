package com.example.dishdash.NetworkCall;




import com.example.dishdash.model.Country;
import com.example.dishdash.model.Meal;

import java.util.List;

public class CountryResponse {
    private List<Meal> meals; // Change to List<Meal>

    public List<Meal> getMeals() { // Change to getMeals()
        return meals;
    }

    public CountryResponse(List<Meal> meals) { // Change to List<Meal>
        this.meals = meals;
    }
}

