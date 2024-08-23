package com.example.dishdash.model.Repository;

import androidx.lifecycle.LiveData;

import com.example.dishdash.NetworkCall.NetworkCallBack;
import com.example.dishdash.model.Meal;

import java.util.List;

public interface MealsRepository {
    void insertMeal(Meal meal);

    void deleteMeal(Meal meal);
    LiveData<List<Meal>> getAllMeals();

    void getAllMeals(NetworkCallBack networkCallBacks);

    LiveData<List<Meal>> getFavMeals(String userId, NetworkCallBack networkCallBacks);

    void updateMeal(Meal meal);
}
