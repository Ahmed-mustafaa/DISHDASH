package com.example.dishdash.NetworkCall;

import android.util.Log;

import java.util.List;


import com.example.dishdash.model.Meal;
import com.example.dishdash.NetworkCall.MealResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MealsRemoteDataSourceImpl {

    public static final String TAG = "RandomMeal";
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private MealService mealServices;
    private MealResponse mResponse;
    private Call<MealResponse> call ;
    private static MealsRemoteDataSourceImpl retClient = null;

    public MealsRemoteDataSourceImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mealServices = retrofit.create(MealService.class);
     call  =  mealServices.getRandom();
    }

    public static synchronized MealsRemoteDataSourceImpl getInstance() {
        if (retClient == null) {
            retClient = new MealsRemoteDataSourceImpl();
        }
        return retClient;
    }


    public void makeNetworkCall(NetworkCallBack ntwrkCallBack) {
        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful()) {
                    Meal meal = response.body().getMeals().get(0);
                    String mealName = meal.getStrMeal();
                    String mealCategory = meal.getStrCategory();
                    String mealArea = meal.getStrArea();
                    String mealInstructions = meal.getStrInstructions();
                    String mealImage = meal.getStrMealThumb();
                    Log.i(TAG, "onResponse: " + meal);
                    ntwrkCallBack.onSuccess(meal);
                    Log.i(TAG, "onSucess: " + meal.toString());
                } else {
                    Log.i(TAG, "Erorrrrr ! : " + response.code());
                }


            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                ntwrkCallBack.onFailure(t); // Notify failure
                //  Toast.makeText(AllProductsActivity.this,  "Error fetching products", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error fetching meal: " + t.getMessage());
            }


        });

    }
}




