package com.example.dishdash.NetworkCall;

import static com.google.android.gms.tasks.Tasks.call;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;


import com.example.dishdash.model.Category;
import com.example.dishdash.model.Country;
import com.example.dishdash.model.Meal;
import com.example.dishdash.view.CountriesAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MealsRemoteDataSourceImpl {

    public static final String TAG = "RandomMeal";
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private MealService mealServices;
    private Service countryService;
    private Service categoryService;
    private CountriesAdapter adapter; // Reference to the adapter


    private Call<MealResponse> randomMealCall;
    private Call<List<Country>> countryCall;
    private Call<MealResponse> breakfastCall;
    private Call<MealResponse> lunchCall;
    private Call<MealResponse> dinnerCall;
    private static MealsRemoteDataSourceImpl retClient = null;

    public MealsRemoteDataSourceImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mealServices = retrofit.create(MealService.class);
        randomMealCall = mealServices.getRandom();
        countryService = retrofit.create(Service.class);
        categoryService = retrofit.create(Service.class);
        breakfastCall = mealServices.getMealsByCategory("breakfast");
        lunchCall = mealServices.getMealsByCategory("Seafood");
        dinnerCall = mealServices.getMealsByCategory("beef");

    }

    public static synchronized MealsRemoteDataSourceImpl getInstance() {
        if (retClient == null) {
            retClient = new MealsRemoteDataSourceImpl();
        }
        return retClient;
    }


    public void makeNetworkCall(NetworkCallBack ntwrkCallBack) {
        randomMealCall.enqueue(new Callback<MealResponse>() {
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
/*
    public void getDailyMeals(DailyMealsCall callback) {
        List<Meal> meals = new ArrayList<>();
        int[] completedRequests = {0};
        final int totalRequests = 3; // Total requests we are making

        Callback<MealResponse> commonCallback = new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null &&response.body().getMeals() != null && !response.body().getMeals().isEmpty()) {
                    meals.add(response.body().getMeals().get(0));
                } else {
                    Log.e(TAG, "Error fetching meal: " + call.request().url());
                }

                completedRequests[0]++;
                if (completedRequests[0] == totalRequests) {
                    callback.OnSuccess(meals); // Notify success when all requests are completed
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                Log.e(TAG, "Error fetching meal: " + t.getMessage());
                completedRequests[0]++;
                if (completedRequests[0] == totalRequests) {
                    callback.OnFailure(new Throwable("Failed to fetch all meals")); // Notify success with partial results if some requests failed
                }
            }
        };

        breakfastCall.enqueue(commonCallback);
        lunchCall.enqueue(commonCallback);
        dinnerCall.enqueue(commonCallback);
    }*/

    public void getCountries(CountryCallBack countryCallBack){
        countryService.getCountries().enqueue(new Callback<CountryResponse> () {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
               if (response.isSuccessful() && response.body() != null) {
                List<Meal> meals = response.body().getMeals();
                List<Country> countries = new ArrayList<>();
                for(Meal meal : meals){
                    countries.add(new Country(meal.getStrArea()));
                }
                countryCallBack.onSuccess(countries);
                Log.i(TAG, "onResponse: " + countries +response.code());
            } else {
                    Log.e(TAG, "Error fetching countries: " + response.code()); // otherwise show this error message with response code
                    countryCallBack.onFailure(new Throwable("Respponse failed to fetch countries due to"));
                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                new Throwable("Error fetching countries: " + t.getMessage());
                adapter.setItems(new ArrayList<>());
            }

        });
    }





public void getCategories(NetworkCallBack categoryCallBack){
    categoryService.getCategories().enqueue(new Callback<CountryResponse> () {
        @Override
        public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
            if (response.isSuccessful() && response.body() != null) {
                List<Meal> meals = response.body().getMeals();
                List<Category> categories = new ArrayList<>();
                for(Meal meal : meals){
                    categories.add(new Category(meal.getStrCategory()));
                }
                categoryCallBack.onSuccessCategory(categories);
                Log.i(TAG, "onResponse: " + categories +response.code());
            } else {
                Log.e(TAG, "Error fetching countries: " + response.code()); // otherwise show this error message with response code
                categoryCallBack.onFailure(new Throwable("Respponse failed to fetch countries due to"));
            }
        }

        @Override
        public void onFailure(Call<CountryResponse> call, Throwable t) {
            new Throwable("Error fetching countries: " + t.getMessage());
            adapter.setItems(new ArrayList<>());
        }




    });
}
}



