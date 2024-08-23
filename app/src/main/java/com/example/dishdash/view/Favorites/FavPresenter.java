package com.example.dishdash.view.Favorites;

import androidx.lifecycle.LiveData;

import com.example.dishdash.NetworkCall.NetworkCallBack;
import com.example.dishdash.model.Meal;

import java.util.List;

public interface FavPresenter extends NetworkCallBack {


    void removeProduct(Meal meal);
    LiveData<List<Meal>> getFavoriteMeals(String userID);
}
