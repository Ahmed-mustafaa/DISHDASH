package com.example.dishdash.presenter;

import com.example.dishdash.NetworkCall.MealsRemoteDataSourceImpl;
import com.example.dishdash.NetworkCall.NetworkCallBack;
import com.example.dishdash.model.Category;
import com.example.dishdash.model.Ingredient;
import com.example.dishdash.model.Meal;
import com.example.dishdash.view.MealDetailsView;

import java.util.List;
import java.util.Map;


public class MealDetailsPresenter {
    private MealDetailsView view;
    private MealsRemoteDataSourceImpl dataSource;;

    public MealDetailsPresenter(MealDetailsView view, MealsRemoteDataSourceImpl dataSource) {
        this.view = view;
        this.dataSource = dataSource;
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
            public void onIngredientsSuccess( List<Map<String,String>> ingredients) {
              view.showIngredients(ingredients);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

}

