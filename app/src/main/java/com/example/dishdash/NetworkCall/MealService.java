package com.example.dishdash.NetworkCall;

import com.example.dishdash.model.Meal;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealService {
    @GET("random.php")
     Call<MealResponse> getRandom();

    @GET("filter.php")
    Call<MealResponse> getMealsByCategory(@Query("c") String category );
}
