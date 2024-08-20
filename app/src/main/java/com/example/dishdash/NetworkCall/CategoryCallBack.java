package com.example.dishdash.NetworkCall;

import com.example.dishdash.model.Category;
import com.example.dishdash.model.Meal;

import java.util.List;

public interface CategoryCallBack {
    void onSuccess(List<Meal> meals);
    void onSuccessCategory(List<Category> categories);

    public void onFailure(Throwable t);
}
