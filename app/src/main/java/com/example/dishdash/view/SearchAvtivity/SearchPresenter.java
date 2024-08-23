package com.example.dishdash.view.SearchAvtivity;

import com.example.dishdash.NetworkCall.MealsRemoteDataSourceImpl;
import com.example.dishdash.NetworkCall.NetworkCallBack;
import com.example.dishdash.NetworkCall.CategoryCallBack;
import com.example.dishdash.NetworkCall.CountryCallBack;
import com.example.dishdash.model.Category;
import com.example.dishdash.model.Country;
import com.example.dishdash.model.Meal;


import java.util.List;
import java.util.Map;
public class SearchPresenter {

    private SearchView searchView;
    private MealsRemoteDataSourceImpl dataSource;

    public SearchPresenter(SearchView view) {
        this.searchView = view;
        this.dataSource = MealsRemoteDataSourceImpl.getInstance();
    }
public void SearcMealByletter(String letter){
    searchView.showLoading();
    dataSource.searchMealsByFirstLetter(letter.charAt(0), new NetworkCallBack() {
        @Override
        public void onSuccess(Meal meal) {

        }

        @Override
        public void onSuccess(List<Meal> meals) {
            searchView.showMealsByLetter(meals);
            searchView.hideLoading();
        }

        @Override
        public void onSuccessCategory(List<Category> categories) {

        }

        @Override
        public void onIngredientsSuccess(List<Map<String, String>> ingredients) {

        }

        @Override
        public void onFailure(Throwable t) {
            searchView.showError(t);
            searchView.hideLoading();
        }

    });
}
    public void loadCountries() {
        searchView.showLoading();
        dataSource.getCountries(new CountryCallBack() {
            @Override
            public void onSuccess(List<Country> countries) {
                searchView.hideLoading();
                searchView.showAllCountries(countries); // Display the countries in the adapter
            }

            @Override
            public void onCountryMealsSuccess(List<Meal> meals) {

            }

            @Override
            public void onFailure(Throwable throwable) {
                searchView.hideLoading();
                searchView.showError(throwable.getMessage());
            }
        });
    }

    public void loadCategories() {
        searchView.showLoading();
        dataSource.getCategories(new NetworkCallBack() {
            @Override
            public void onSuccess(Meal meal) {

            }

            @Override
            public void onSuccess(List<Meal> meal) {

            }

            @Override
            public void onSuccessCategory(List<Category> categories) {
                searchView.hideLoading();
                searchView.showAllCategories(categories); // Display the categories in the adapter
            }

            @Override
            public void onIngredientsSuccess(List<Map<String, String>> ingredients) {

            }

            @Override
            public void onFailure(Throwable throwable) {
                searchView.hideLoading();
                searchView.showError(throwable.getMessage());
            }
        });
    }
}