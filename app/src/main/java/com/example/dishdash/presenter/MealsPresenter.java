package com.example.dishdash.presenter;

import androidx.annotation.NonNull;

import com.example.dishdash.NetworkCall.CountryCallBack;
import com.example.dishdash.NetworkCall.DailyMealsCall;
import com.example.dishdash.NetworkCall.MealResponse;
import com.example.dishdash.NetworkCall.MealService;
import com.example.dishdash.NetworkCall.MealsRemoteDataSourceImpl;
import com.example.dishdash.NetworkCall.NetworkCallBack;
import com.example.dishdash.model.Category;
import com.example.dishdash.model.Country;
import com.example.dishdash.model.Meal;
import com.example.dishdash.view.MealsView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsPresenter {
    private MealsView view;
    private MealService mealService;
    private MealsRemoteDataSourceImpl MealsRemoteDataSourceImpl;

    public MealsPresenter(MealsView view, MealsRemoteDataSourceImpl MealsRemoteDataSourceImpl) {
        this.view = view;
        this.MealsRemoteDataSourceImpl = MealsRemoteDataSourceImpl;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.themealdb.com//api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Initialize mealService
        mealService = retrofit.create(MealService.class);
    }

public void getDailyMeals() {
    MealsRemoteDataSourceImpl.getCountries(new CountryCallBack() {
        @Override
        public void onSuccess(List<Country> countries) {
            if(view != null){
                view.showAllCountries(countries);
            }
        }

        @Override
        public void onFailure(Throwable throwable) {

            if (view != null) {
                view.showError(throwable.getMessage());
            }
        }


    });


MealsRemoteDataSourceImpl.getCategories(new NetworkCallBack() {


    @Override
    public void onSuccess(Meal meal) {

    }

    @Override
    public void onSuccessCategory(List<Category> categories) {
        if (view != null) {
            view.showAllCategories(categories);
        }
    }

    @Override
    public void onFailure(Throwable t) {

    }


});

}
    public void getRandom() {
        mealService.getRandom().enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(@NonNull Call<MealResponse> call, @NonNull Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.showRandomMeal(response.body().getMeals().get(0));
                } else {
                    view.showError("Error fetching meal");

                }
            }

            @Override
            public void onFailure(@NonNull Call<MealResponse> call, Throwable t) {
                view.showError("Network failure: " + t.getMessage());


            }
        });
    }
}


