package com.example.dishdash.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.dishdash.model.Meal;

import java.util.List;

public class MealsLocalDataSourceImpl implements MealsLocalDataSource {
    private MealDAO mealDAO;
    private LiveData<List<Meal>> storedProducts;
    public MealsLocalDataSourceImpl(Context context){
        if(context == null) {
            throw new NullPointerException("Context is null in MealsLocalDataSourceImpl");
        }
            AppDataBase db = AppDataBase.getInstance(context.getApplicationContext());
        mealDAO = db.getProductDAO();


        storedProducts = mealDAO.getAllProducts();


    }
    private static MealsLocalDataSourceImpl instance = null;

    public static synchronized MealsLocalDataSourceImpl getInstance(Context context) {
        if (instance == null) {
            instance = new MealsLocalDataSourceImpl(context.getApplicationContext());
        }
        return instance;
    }


    @Override
    public void insertMeal(Meal meal) {
        new Thread(()->
                mealDAO.insertMeal(meal)).start();
    }

    @Override
    public void deleteMeal(Meal meal) {
        new Thread(()->
                mealDAO.deleteMeal(meal)).start();
    }

    @Override
    public LiveData<List<Meal>> getSortedMeals() {
        return storedProducts;
    }

    @Override
    public MealDAO mealDAO() {
        return mealDAO;
    }

    @Override
    public void updateMeal(Meal meal) {

    }

    public LiveData<List<Meal>> getFavoritesByUserId(String userId) {
        return mealDAO.getFavoritesByUserId(userId);
    }
}
