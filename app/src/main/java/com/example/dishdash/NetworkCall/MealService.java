package com.example.dishdash.NetworkCall;

import com.example.dishdash.model.Meal;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealService {
    @GET("random.php")
     Call<MealResponse> getRandom();
}
