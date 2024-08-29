package com.example.dishdash.presenter;

import android.view.View;

import com.example.dishdash.NetworkCall.MealsRemoteDataSourceImpl;
import com.example.dishdash.NetworkCall.NetworkCallBack;
import com.example.dishdash.db.MealsLocalDataSourceImpl;
import com.example.dishdash.model.Category;
import com.example.dishdash.model.Meal;
import com.example.dishdash.model.Repository.MealsRepositoryImpl;
import com.example.dishdash.view.MealDetails.MealDetailsView;

import java.util.List;
import java.util.Map;


public class MealDetailsPresenter {
    private MealDetailsView view;
    private MealsRemoteDataSourceImpl dataSource;
    private MealsLocalDataSourceImpl localDataSource;
    private  MealsRepositoryImpl repository;

    public MealDetailsPresenter(MealDetailsView view, MealsRemoteDataSourceImpl dataSource, MealsLocalDataSourceImpl localDataSource) {
        this.view = view;
        this.dataSource = dataSource;
        this.localDataSource = localDataSource;


        this.repository = MealsRepositoryImpl.getInstance(dataSource, localDataSource);

    }
    public void getMealDetails(int mealId) {

        dataSource.getMealDetails(mealId, new NetworkCallBack() {
            @Override
            public void onSuccess(Meal meal) {
                view.showMealDetails(meal);
            }

            @Override
            public void onSuccess(List<Meal> meal) {

            }

            @Override
            public void onSuccessCategory(List<Category> categories) {

            }

            @Override
            public void onIngredientsSuccess(List<Map<String, String>> ingredients) {
                view.showIngredients(ingredients);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
    public void addMealToFavorites(Meal meal) {
        if (localDataSource != null) {
            localDataSource.insertMeal(meal);
        } else {
            view.showErrorMessage("Error: Unable to add meal to favorites.");
        }
    }

    // Remove the meal from the favorites in the local database
    public void removeMealFromFavorites(Meal meal) {
        if (localDataSource != null) {
            localDataSource.deleteMeal(meal);
        } else {
            view.showErrorMessage("Error: Unable to remove meal from favorites.");
        }
        }

    }



