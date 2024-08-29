package com.example.dishdash.view.Favorites;

import com.example.dishdash.model.Meal;


import java.util.List;

public interface FavoritesView {
    void setMeals(List<Meal> meals);
    void displayFavoriteMeals(List<Meal> meals);
    void showEmptyFavoritesMessage();
    }


