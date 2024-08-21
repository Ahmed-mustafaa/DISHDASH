package com.example.dishdash.presenter;

import com.example.dishdash.NetworkCall.CategoryCallBack;
import com.example.dishdash.NetworkCall.CountryCallBack;
import com.example.dishdash.NetworkCall.MealService;
import com.example.dishdash.NetworkCall.MealsRemoteDataSourceImpl;
import com.example.dishdash.model.Category;
import com.example.dishdash.model.Country;
import com.example.dishdash.model.Meal;
import com.example.dishdash.view.CategoryMealsView;

import java.util.List;

public class FilterMealsPresenter {
    private final CategoryMealsView view;
    private MealService mealService;
    private MealsRemoteDataSourceImpl MealsRemoteDataSourceImpl;

    public FilterMealsPresenter(CategoryMealsView view, MealsRemoteDataSourceImpl mealsRemoteDataSourceImpl) {
        this.view = view;
        this.MealsRemoteDataSourceImpl = mealsRemoteDataSourceImpl;
    }
    public void fetchMealsByCategories(String categoryName){
    view.showLoading();
        MealsRemoteDataSourceImpl.fetchMealsByCategories(categoryName, new CategoryCallBack() {
            @Override
            public void onSuccess(List<Meal> meals) {
                view.hideLoading();
                view.showMeals(meals);
            }

            @Override
            public void onSuccessCategory(List<Category> categories) {
                 view.hideLoading();
            }

            @Override
            public void onFailure(Throwable t) {
                 view.hideLoading();
                 view.showError(t.getMessage());
            }
        });


    }
    public void fetchMealsByCountries(String countryname){
        view.showLoading();
        MealsRemoteDataSourceImpl.fetchMealsByCountries(countryname, new CountryCallBack() {

            @Override
            public void onSuccess(List<Country> countries) {

            }

            @Override
            public void onCountryMealsSuccess(List<Meal> meals) {
                view.hideLoading();
                view.showMeals(meals);
            }

            @Override
            public void onFailure(Throwable t) {
                view.hideLoading();
                view.showError(t.getMessage());
            }
        });


    }
}
