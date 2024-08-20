package com.example.dishdash.NetworkCall;

import com.example.dishdash.model.Meal;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("random.php")
     Call<MealResponse> getRandom();

    @GET("categories.php")
    Call<CategoryResponse> getMealsByCategory();


    @GET("filter.php")
    Call<CategoryResponse> getMealsByCategory(@Query("c") String category);

}
