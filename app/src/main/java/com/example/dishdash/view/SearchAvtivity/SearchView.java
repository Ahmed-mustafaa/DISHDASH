package com.example.dishdash.view.SearchAvtivity;

import java.util.List;
import com.example.dishdash.model.Meal;
import com.example.dishdash.model.Category;
import com.example.dishdash.model.Country;
public interface SearchView {
    void showMealsByLetter(List<Meal> meals);
    void showMealsByCategory(List<Meal> meals);
    void showMealsByCountry(List<Meal> meals);
    void showAllCountries(List<Country> countries);
    void showAllCategories(List<Category> categories);
    void showError(String message);
    void showError(Throwable throwable);
    void showLoading();
    void hideLoading();
}