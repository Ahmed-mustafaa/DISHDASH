package com.example.dishdash.NetworkCall;

import static android.media.CamcorderProfile.get;
import static com.google.android.gms.tasks.Tasks.call;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    public void getAllMeals(){
        mealServices.getMealsByCategory().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Category> categories = response.body().getCategories();
                    for (Category category : categories) {
                        getMealsByCategory(category.getStrCategory());
                    }
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {

            }
        });
    }

    private void getMealsByCategory(String category) {
        mealServices.getMealsByCategory(category).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Meal> meals = response.body().getMeals();
                    for (Meal meal : meals) {
                        Log.d("Meal", "Meal Name: " + meal.getStrMeal());
                    }
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {

            }
        });
    }

    public void getMealDetails(int mealId , NetworkCallBack netwrkCallBack) {
        mealServices.getMealById(mealId).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful()) {
                    MealResponse mealResponse = response.body();
                    if (mealResponse != null && mealResponse.getMeals() != null) {
                        List<Meal> meals = mealResponse.getMeals();
                        if (!meals.isEmpty()) {
                            Meal meal = meals.get(0);
                            List<Map<String, String>> ingredients = ingredientDetails(meal);
                            netwrkCallBack.onSuccess(meal);
                            netwrkCallBack.onIngredientsSuccess(ingredients);
                            Log.i(TAG, "onSuccess: " + meal.toString());
                        } else {
                            Log.i(TAG, "No meals found in response");
                            netwrkCallBack.onFailure(new Exception("No meals found in response"));
                        }
                    } else {
                        Log.i(TAG, "Response body or meals is null");
                        netwrkCallBack.onFailure(new Exception("Response body or meals is null"));
                    }
                } else {
                    Log.i(TAG, "Error code: " + response.code());
                    netwrkCallBack.onFailure(new Exception("Response not successful, code: " + response.code()));
                }

            }

            private List<Map<String, String>> ingredientDetails(Meal meal) {
                String IngredientBaseUrl = "https://www.themealdb.com/images/ingredients/";
                String Extention = "-Small.png";
                List<Map<String, String>> ingredients = new ArrayList<>();
                String name;
                String measure;
                for (int i = 1; i <= 20; i++) {
                    switch (i) {
                        case 1:
                            name = meal.strIngredient1;
                            measure = meal.strMeasure1;
                            break;
                        case 2:
                            name = meal.strIngredient2;
                            measure = meal.strMeasure2;
                            break;
                        case 3:
                            name = meal.strIngredient3;
                            measure = meal.strMeasure3;
                            break;
                        case 4:
                            name = meal.strIngredient4;
                            measure = meal.strMeasure4;
                            break;
                        case 5:
                            name = meal.strIngredient5;
                            measure = meal.strMeasure5;
                            break;
                        case 6:
                            name = meal.strIngredient6;
                            measure = meal.strMeasure6;
                            break;
                        case 7:
                            name = meal.strIngredient7;
                            measure = meal.strMeasure7;
                            break;
                        case 8:
                            name = meal.strIngredient8;
                            measure = meal.strMeasure8;
                            break;
                        case 9:
                            name = meal.strIngredient9;
                            measure = meal.strMeasure9;
                            break;
                        case 10:
                            name = meal.strIngredient10;
                            measure = meal.strMeasure10;
                            break;
                        case 11:
                            name = meal.strIngredient11;
                            measure = meal.strMeasure11;
                            break;
                        case 12:
                            name = meal.strIngredient12;
                            measure = meal.strMeasure12;
                            break;
                        case 13:
                            name = meal.strIngredient13;
                            measure = meal.strMeasure13;
                            break;
                        case 14:
                            name = meal.strIngredient14;
                            measure = meal.strMeasure14;
                            break;
                        case 15:
                            name = meal.strIngredient15;
                            measure = meal.strMeasure15;
                            break;
                        case 16:
                            name = meal.strIngredient16;
                            measure = meal.strMeasure16;
                            break;
                        case 17:
                            name = meal.strIngredient17;
                            measure = meal.strMeasure17;
                            break;
                        case 18:
                            name = meal.strIngredient18;
                            measure = meal.strMeasure18;
                            break;
                        case 19:
                            name = meal.strIngredient19;
                            measure = meal.strMeasure19;
                            break;
                        case 20:
                            name = meal.strIngredient20;
                            measure = meal.strMeasure20;
                            break;
                        default:
                            name = null;
                            measure = null;
                            break;
                    }
                        if (name != null && measure != null && !name.isEmpty()) {
                            String imageUrl = IngredientBaseUrl + name + Extention;
                            Map<String, String> ingredient = new HashMap<>();
                            ingredient.put("name", name);
                            ingredient.put("measure", measure);
                            ingredient.put("imageUrl", imageUrl);
                            ingredients.add(ingredient);


                        }
                    }

                return ingredients;

            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                netwrkCallBack.onFailure(t);
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



