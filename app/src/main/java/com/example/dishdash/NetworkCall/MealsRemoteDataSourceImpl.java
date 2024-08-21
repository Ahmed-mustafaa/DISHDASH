package com.example.dishdash.NetworkCall;

import static com.google.android.gms.tasks.Tasks.call;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;


import com.example.dishdash.model.Category;
import com.example.dishdash.model.Country;
import com.example.dishdash.model.DisplayItem;
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
    private MealService categoryService;
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
        categoryService = retrofit.create(MealService.class);


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
    public void getMealDetails(int mealId , NetworkCallBack netwrkCallBack) {
        randomMealCall.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful()) {
                    Meal meal = response.body().getMeals().get(0);
                    int mealId = Integer.parseInt(meal.getIdMeal());
                    String mealName = meal.getStrMeal();
                    String mealCategory = meal.getStrCategory();
                    String mealArea = meal.getStrArea();
                    String mealInstructions = meal.getStrInstructions();
                    String mealImage = meal.getStrMealThumb();


                    Log.i(TAG, "onResponse: " + meal);
                    netwrkCallBack.onSuccess(meal);
                    Log.i(TAG, "onSucess: " + meal.toString());
                } else {
                    Log.i(TAG, "Erorrrrr ! : " + response.code());
                }


            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                netwrkCallBack.onFailure(t); // Notify failure
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
public void getCategories(NetworkCallBack categoryCallBack) {
    categoryService.getMealsByCategory().enqueue(new Callback<CategoryResponse>() {
        @Override
        public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
            if (response.isSuccessful() && response.body() != null) {
                List<Category> categories = response.body().getCategories();
                List<DisplayItem> items = new ArrayList<>();
                for (Category category : categories) {
                    DisplayItem item = new DisplayItem(DisplayItem.ItemType.CATEGORY,
                            category.getStrCategory(),
                            category.getStrCategoryThumb());
                    item.setImageUrl(category.getStrCategoryThumb());
                    item.setStrCategory(category.getStrCategory());
                    items.add(item);
                }
                categoryCallBack.onSuccessCategory(categories);

               /* for(Meal meal : meals){
                    categories.add(new Category(meal.getStrCategory()));
                }*/

                Log.i(TAG, "onResponse: " + categories + response.code());
            } else {
                Log.e(TAG, "Error fetching categories: " + response.code()); // otherwise show this error message with response code
                categoryCallBack.onFailure(new Throwable("Respponse failed to fetch countries due to"));
            }
        }

        @Override
        public void onFailure(Call<CategoryResponse> call, Throwable t) {
            new Throwable("Error fetching categories : " + t.getMessage());
            adapter.setItems(new ArrayList<>());
        }
    });
}
public void fetchMealsByCategories(String categoryname,CategoryCallBack callBack){
    Call<MealResponse> call = mealServices.getMealsByCategory(categoryname);
    call.enqueue(new Callback<MealResponse>() {
        @Override
        public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
            if (response.isSuccessful() && response.body() != null) {
                List<Meal> meals = response.body().getMeals();
                callBack.onSuccess(meals);
            } else {
                callBack.onFailure(new Throwable("Failed to fetch meals for category: " + categoryname));
            }
        }

        @Override
        public void onFailure(Call<MealResponse> call, Throwable t) {
            new Throwable("Error fetching meals for category: " + categoryname);
        }


    });
}
public void fetchMealsByCountries(String countryname,CountryCallBack callBack){
    Call<MealResponse> call = countryService.getMealsByCountry(countryname);
    call.enqueue(new Callback<MealResponse>() {
        @Override
        public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
            if (response.isSuccessful() && response.body() != null) {
                List<Meal> meals = response.body().getMeals();
                callBack.onCountryMealsSuccess(meals);
            } else {
                callBack.onFailure(new Throwable("Failed to fetch meals for category: " + countryname));
            }
        }

        @Override
        public void onFailure(Call<MealResponse> call, Throwable t) {
            new Throwable("Error fetching meals for category: " + countryname);
        }


    });
}
}



