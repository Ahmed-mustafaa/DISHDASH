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
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.dishdash.NetworkCall.CategoryCallBack;
import com.example.dishdash.NetworkCall.MealsRemoteDataSourceImpl;
import com.example.dishdash.R;
import com.example.dishdash.model.Category;
import com.example.dishdash.model.DisplayItem;
import com.example.dishdash.model.Meal;
import com.example.dishdash.presenter.CategoryMealsPresenter;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity implements CategoryMealsView{
public static final String NAMEFROMDASHBORADACTIVITY = "Name from Dashboard Activity";
    MealsAdapter MealsAdapter;
    private List<Meal> meals = new ArrayList<>(); // Declare items as a class member

    public  CategoryMealsPresenter presenter;
  CategoryMealsView view;
    private TextView mealDescription;
    private TextView greeting;

    CardView cardView;
    LottieAnimationView progress;
    RecyclerView Rec;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_country_meals_layout); // Or your layout file

        MealsAdapter = new MealsAdapter(this, meals, true);
        String categoryName = null;
        String countryName = null;
        Intent intent = getIntent();
        if (intent.hasExtra("categoryName")) {
            categoryName = intent.getStringExtra("categoryName");
            Log.i(NAMEFROMDASHBORADACTIVITY, "onCreate: " + categoryName);
        } else if (intent.hasExtra("countryName")) {
            countryName = intent.getStringExtra("countryName");
            Log.i(NAMEFROMDASHBORADACTIVITY, "onCreate: " + countryName);
        }

        Rec = findViewById(R.id.meals_recyclerview);
        greeting = findViewById(R.id.user_greeting);
        progress = findViewById(R.id.lottieProgress);
        cardView = findViewById(R.id.user_greeting_card);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                hideLoading();
            }
        }, 6000);
        presenter = new CategoryMealsPresenter(this, new MealsRemoteDataSourceImpl());
        if (categoryName != null) {
            presenter.fetchMealsByCategories(categoryName, new CategoryCallBack() {
                @Override
                public void onSuccess(List<Meal> meals) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MealsAdapter.setMeals(meals, false);
                            Rec.setAdapter(MealsAdapter);

                        }
                    });
                }

                @Override
                public void onSuccessCategory(List<Category> categories) {

                }

                @Override
                public void onFailure(Throwable t) {
                    new Throwable(t.getMessage());
                }
            });

        }
       /* else if (countryName != null) {
            presenter.fetchMealsByCountry(countryName, new CategoryCallBack() {
                @Override
                public void onSuccess(List<Meal> meals) {
                    // Handle meals for country
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mealsAdapter.setMeals(meals, true); // Assuming true indicates country meals
                            mealsRecyclerView.setAdapter(mealsAdapter);
                        }
                    });
                }*/
    }


    @Override
    public void showLoading() {

        progress.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoading() {
        progress.setVisibility(View.GONE);
        Rec.setVisibility(View.VISIBLE);
        greeting.setVisibility(View.VISIBLE);
        cardView.findViewById(View.VISIBLE);

    }

    @Override
    public void showMeals(List<Meal> meals) {

    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, "Erorr", Toast.LENGTH_SHORT);
    }
}
