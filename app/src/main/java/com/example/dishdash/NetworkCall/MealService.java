package com.example.dishdash.NetworkCall;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("random.php")
     Call<MealResponse> getRandom();

    @GET("categories.php")
    Call<CategoryResponse> getMealsByCategory();


    @GET("filter.php")
    Call<MealResponse> getMealsByCategory(@Query("c") String category);

    @GET("lookup.php")
    Call<MealResponse> getMealById(@Query("i") int id);
    @GET("search.php")
    Call<MealResponse> getMealsByFirstLetter(@Query("f") String letter);


}
