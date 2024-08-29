package com.example.dishdash.presenter;

import com.example.dishdash.NetworkCall.CategoryCallBack;
import com.example.dishdash.NetworkCall.CountryCallBack;
import com.example.dishdash.NetworkCall.MealsRemoteDataSourceImpl;
import com.example.dishdash.model.Category;
import com.example.dishdash.model.Country;
import com.example.dishdash.model.Meal;
import com.example.dishdash.model.Repository.MealsRepositoryImpl;
import com.example.dishdash.view.FilterationScreen.CategoryMealsView;

import java.util.List;



    public class FilterMealsPresenter {
        private final CategoryMealsView view;
        private final MealsRemoteDataSourceImpl mealsRemoteDataSource;
        private final MealsRepositoryImpl repository;

        public FilterMealsPresenter(CategoryMealsView view, MealsRepositoryImpl repository,MealsRemoteDataSourceImpl dataSource) {
            this.view = view;
            this.repository = repository;
            this.mealsRemoteDataSource = dataSource;
        }

        public void fetchMealsByCategories(String categoryName) {
            view.showLoading();
            mealsRemoteDataSource.fetchMealsByCategories(categoryName, new CategoryCallBack() {
                @Override
                public void onSuccess(List<Meal> meals) {
                    view.hideLoading();
                    view.showMeals(meals);
                }

                @Override
                public void onSuccessCategory(List<Category> categories) {

                }

                @Override
                public void onFailure(Throwable t) {
                    view.hideLoading();
                    view.showError(t.getMessage());
                }
            });
        }

        public void fetchMealsByCountries(String countryName) {
            view.showLoading();
            mealsRemoteDataSource.fetchMealsByCountries(countryName, new CountryCallBack() {
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
