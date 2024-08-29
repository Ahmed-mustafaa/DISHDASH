package com.example.dishdash.view.FilterationScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.dishdash.NetworkCall.MealsRemoteDataSourceImpl;
import com.example.dishdash.R;
import com.example.dishdash.db.AppData;
import com.example.dishdash.db.MealsLocalDataSourceImpl;
import com.example.dishdash.model.Meal;
import com.example.dishdash.model.Repository.MealsRepositoryImpl;
import com.example.dishdash.presenter.FilterMealsPresenter;
import com.example.dishdash.view.MealsAdapter;

import java.util.ArrayList;
import java.util.List;

public class FilterationActivity extends AppCompatActivity implements CategoryMealsView {

    private static final String TAG = "FilterationActivity";
    private MealsAdapter mealsAdapter;
    private List<Meal> meals = new ArrayList<>();
    private FilterMealsPresenter presenter;
    private TextView description;
    private TextView greeting;
    private CardView cardView;
    private LottieAnimationView progress;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_country_meals_layout); // Your layout file
        AppData.getInstance().initialize(this);
        String userID = AppData.getInstance().getUserId();
        Log.i(TAG, "User ID: " + userID);
        recyclerView = findViewById(R.id.meals_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columns

        mealsAdapter = new MealsAdapter(this, meals, true,userID);
        recyclerView.setAdapter(mealsAdapter); // Set adapter initially

        description = findViewById(R.id.Country);
        greeting = findViewById(R.id.user_greeting);
        progress = findViewById(R.id.lottieProgress);
        cardView = findViewById(R.id.user_greeting_card);

        Intent intent = getIntent();
        String categoryName = intent.getStringExtra("categoryName");
        String countryName = intent.getStringExtra("countryName");

        if (categoryName != null) {
            description.setText(categoryName);
            Log.i(TAG, "Category Name: " + categoryName);
        } else if (countryName != null) {
            description.setText(countryName);
            Log.i(TAG, "Country Name: " + countryName);
        }



        presenter = new FilterMealsPresenter(this, MealsRepositoryImpl.getInstance(
                        MealsRemoteDataSourceImpl.getInstance(getApplicationContext()),
                        MealsLocalDataSourceImpl.getInstance(this)
                ), MealsRemoteDataSourceImpl.getInstance(getApplicationContext())
        );

        if (categoryName != null) {
            presenter.fetchMealsByCategories(categoryName);
        } else if (countryName != null) {
            presenter.fetchMealsByCountries(countryName);
        }

        showLoading();
    }

    @Override
    public void showLoading() {
        progress.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE); // Hide RecyclerView while loading
    }

    @Override
    public void hideLoading() {
        progress.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE); // Show RecyclerView after loading
    }

    @Override
    public void showMeals(List<Meal> meals) {
        hideLoading();
        if (meals != null && !meals.isEmpty()) {
            this.meals.clear();
            this.meals.addAll(meals);
            mealsAdapter.notifyDataSetChanged(); // Notify adapter of data change
            Log.i(TAG, "Meals: " + meals);
        } else {
            Toast.makeText(this, "No meals available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showError(String message) {
        hideLoading();
        Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show();
    }
}
