package com.example.dishdash.NetworkCall;

import com.example.dishdash.model.Category;
import com.example.dishdash.model.Meal;

import java.util.List;
import java.util.Map;

public interface NetworkCallBack {
    void onSuccess(Meal meal);
    void onSuccess(List<Meal> meal);
    void onSuccessCategory(List<Category> categories);
     void onIngredientsSuccess( List<Map<String,String>>ingredients);
     void onFailure(Throwable t);
}
