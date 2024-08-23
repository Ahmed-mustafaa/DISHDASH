package com.example.dishdash.view.Favorites;

import android.util.Log;

import androidx.lifecycle.LiveData;

import androidx.lifecycle.LiveData;

import com.example.dishdash.model.Category;
import com.example.dishdash.model.Meal;
import com.example.dishdash.model.Repository.MealsRepositoryImpl;
import com.example.dishdash.NetworkCall.NetworkCallBack;
import com.example.dishdash.view.MealsView;

import java.util.List;
import java.util.Map;

public class FavoritesPresenter implements FavPresenter {

    private static final String TAG = "FavPresenterrrr" ;
    private MealsRepositoryImpl repository;
    private FavoritesView view;

    public FavoritesPresenter(FavoritesView view, MealsRepositoryImpl repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public LiveData<List<Meal>> getFavoriteMeals(String userId) {
        repository.getFavMeals(userId,new NetworkCallBack() {

            @Override
            public void onSuccess(Meal meal) {

            }

            @Override
            public void onSuccess(List<Meal> products) {
                view.displayFavoriteMeals(products);
            }

            @Override
            public void onSuccessCategory(List<Category> categories) {

            }

            @Override
            public void onIngredientsSuccess(List<Map<String, String>> ingredients) {

            }

            @Override
            public void onFailure(Throwable t) {
                view.showEmptyFavoritesMessage();
            }
        });
        return null;
    }

    @Override
    public void removeProduct(Meal meal) {
        meal.setFavorite(false);
        repository.deleteMeal(meal);
        Log.i(TAG, "removeProduct: Item removed from DB" );
        // Consider refreshing the view or notifying the adapter
    }


    @Override
    public void onSuccess(Meal meal) {

    }

    @Override
    public void onSuccess(List<Meal> meals) {
        Log.i(TAG, "Success:" + meals.size() + " Items in DB ");
    }

    @Override
    public void onSuccessCategory(List<Category> categories) {

    }

    @Override
    public void onIngredientsSuccess(List<Map<String, String>> ingredients) {

    }

    @Override
    public void onFailure(Throwable t) {
        Log.i(TAG, "onFailure: Failure ");
        view.showEmptyFavoritesMessage(); // Show error to the user
    }
}



