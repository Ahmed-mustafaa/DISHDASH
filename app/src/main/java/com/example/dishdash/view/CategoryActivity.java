package com.example.dishdash.view;

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
import com.example.dishdash.model.Meal;
import com.example.dishdash.presenter.FilterMealsPresenter;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity implements CategoryMealsView {
    public static final String NAMEFROMDASHBORADACTIVITY = "Name from Dashboard Activity";
    MealsAdapter MealsAdapter;
    private List<Meal> meals = new ArrayList<>(); // Declare items as a class member

    public FilterMealsPresenter presenter;
    CategoryMealsView view;
    private TextView Description;
    private TextView greeting;

    CardView cardView;
    LottieAnimationView progress;
    RecyclerView Rec;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_country_meals_layout); // Or your layout file
        Rec = findViewById(R.id.meals_recyclerview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2); // 2 columns
        MealsAdapter = new MealsAdapter(this, meals, true);
        Rec.setAdapter(MealsAdapter); // Set adapter initially
Description = findViewById(R.id.Country);
        String categoryName = null;
        String countryName = null;
        Intent intent = getIntent();
        if (intent.hasExtra("categoryName")) {
            categoryName = intent.getStringExtra("categoryName");
            Description.setText(categoryName);
            Log.i(NAMEFROMDASHBORADACTIVITY, "onCreate: " + categoryName);
        } else if (intent.hasExtra("countryName")) {
            countryName = intent.getStringExtra("countryName");
            Description.setText(countryName);
            Log.i(NAMEFROMDASHBORADACTIVITY, "onCreate: " + countryName);
        }
        AppData.getInstance().initialize(this);
        String userID = AppData.getInstance().getUserId();
        Log.i(NAMEFROMDASHBORADACTIVITY, "USerID from Category activity: " + userID);
        greeting = findViewById(R.id.user_greeting);
        progress = findViewById(R.id.lottieProgress);
        cardView = findViewById(R.id.user_greeting_card);
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                progress.setVisibility(View.GONE);
                cardView.setVisibility(View.VISIBLE);
                Rec.setVisibility(View.VISIBLE);
                greeting.setVisibility(View.VISIBLE);
                cardView.findViewById(View.VISIBLE);

            }
        });

        presenter = new FilterMealsPresenter(this, new MealsRemoteDataSourceImpl());
        if (categoryName != null) {
            presenter.fetchMealsByCategories(categoryName);

        } else if (countryName != null) {
            presenter.fetchMealsByCountries(countryName);
        }

        showLoading();
    }



        @Override
        public void showLoading () {
            Handler handler = new Handler();


            progress.setVisibility(View.VISIBLE);

        }


        @Override
        public void hideLoading () {
            progress.setVisibility(View.GONE);


        }

        @Override
        public void showMeals (List < Meal > meals) {
            hideLoading();
            if (meals != null && !meals.isEmpty()) {
                this.meals.clear();
                this.meals.addAll(meals);
                MealsAdapter.setMeals(meals, true);
                Log.i(NAMEFROMDASHBORADACTIVITY, "showMeals: " + meals);
                // Update the adapter's data
            } else {
                Toast.makeText(this, "No meals available", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void showError (String message){
            Toast.makeText(this, "Erorr", Toast.LENGTH_SHORT).show();
        }
    }

