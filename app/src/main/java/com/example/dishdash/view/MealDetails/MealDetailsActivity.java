package com.example.dishdash.view.MealDetails;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.example.dishdash.NetworkCall.MealsRemoteDataSourceImpl;
import com.example.dishdash.R;
import com.example.dishdash.db.AppData;
import com.example.dishdash.db.MealsLocalDataSourceImpl;
import com.example.dishdash.model.Meal;
import com.example.dishdash.model.Repository.MealsRepositoryImpl;
import com.example.dishdash.presenter.MealDetailsPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MealDetailsActivity extends AppCompatActivity implements MealDetailsView {

    MealDetailsPresenter presenter;

    MealsRepositoryImpl repository;
    private String mealName;
    private int mealId;
    TextView tv_meal_title;
    private Meal meal;
    TextView mealDirections;
    ImageView meal_image;
    boolean isFavorite = false;
    RecyclerView ingredeintientsRecyclerView;
    LottieAnimationView Heart;
    IngredientsAdapter adapter;
    boolean isGuest;
    WebView webView;
    private List<Map<String, String>> ingredientsList = new ArrayList<>();

    private static final String MEALDETAILSACTIVITY = "MealDetailsActivity";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_details);
        MealsLocalDataSourceImpl  localDataSource =  MealsLocalDataSourceImpl.getInstance(this);
        MealsRemoteDataSourceImpl remoteDataSource =  new MealsRemoteDataSourceImpl(getApplicationContext());
         repository = MealsRepositoryImpl.getInstance(remoteDataSource, localDataSource);

        presenter = new MealDetailsPresenter(this, remoteDataSource, localDataSource);

         isGuest = AppData.getInstance().isGuest();

        meal_image = findViewById(R.id.meal_image);
        tv_meal_title = findViewById(R.id.tv_meal_title);
        mealDirections = findViewById(R.id.mealDirections);
        webView = findViewById(R.id.webView);
        Heart = findViewById(R.id.heart_icon);


        if (getIntent() != null && getIntent().hasExtra("mealId")) {
            mealId = getIntent().getIntExtra("mealId", -1);
            ingredeintientsRecyclerView = findViewById(R.id.ingredientsRecyclerView);
            adapter = new IngredientsAdapter(this, ingredientsList);
            ingredeintientsRecyclerView.setAdapter(adapter);
            ingredeintientsRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            // Default to -1 if not found
        }



        String userId = getIntent().getStringExtra("userId");
        if (userId != null) {
            AppData.getInstance().setUserId(userId);
            Log.i(MEALDETAILSACTIVITY, "USER ID IS : " + userId);
        } else {
            Log.w(MEALDETAILSACTIVITY, "User ID not found in Intent");
        }

        if (mealId != -1) {
            presenter.getMealDetails(mealId);
            Log.i(MEALDETAILSACTIVITY, " MEAL ID IS : " + mealId);
        } else {
            Toast.makeText(this, "Meal ID not found", Toast.LENGTH_SHORT).show();
        }


        Heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (meal != null) {
                    toggleFavoriteMeal();
                } else {
                    Toast.makeText(MealDetailsActivity.this, "Meal not loaded yet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void toggleFavoriteMeal() {
        isFavorite = !isFavorite;
        if (isFavorite) {
            Heart.playAnimation(); // Play the Lottie animation for adding to favorites
            addToFavorites(meal);      // Call method to add the meal to favorites
        } else {
            Heart.setProgress(0);  // Reset the Lottie animation (optional if you want to reverse it)
            removeFromFavorites(meal); // Call method to remove the meal from favorites
        }
    }
    private void addToFavorites(Meal meal) {
        String userId = AppData.getInstance().getUserId(); // Get the user ID
        if (userId == null) {
            AppData.getInstance().showLoginPrompt();
            return;
        }
        meal.setUserId(userId);
        meal.setFavorite(true); // Assuming Meal model has a favorite flag
        presenter.addMealToFavorites(meal); // Save the meal as favorite using the presenter or local database
        Toast.makeText(this, meal.getStrMeal() + " added to favorites", Toast.LENGTH_SHORT).show();
    }
    private void removeFromFavorites(Meal meal) {
        if (meal != null) {
            meal.setFavorite(false); // Mark the meal as not a favorite
            presenter.removeMealFromFavorites(meal); // Remove meal from local database
            Toast.makeText(this, meal.getStrMeal() + " removed from favorites", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void showMealDetails(Meal meal) {
        this.meal = meal;
        Log.i(MEALDETAILSACTIVITY, "showMealDetails: "+ meal.getStrMealThumb());
        Glide.with(this).load(meal.getStrMealThumb()).diskCacheStrategy(DiskCacheStrategy.ALL).override(Target.SIZE_ORIGINAL).into(meal_image);

        Log.i(MEALDETAILSACTIVITY, "showMealDetails - GLID IS : " + meal.getStrMealThumb() );
        tv_meal_title.setText(meal.getStrMeal());
        Log.i(MEALDETAILSACTIVITY, "showMealDetails - TILTE IS : " + meal.getStrMeal() );
        mealDirections.setText(meal.getStrInstructions());
        String videoUrl = meal.getStrYoutube(); // Assuming this is where the video URL is stored
        if (videoUrl != null && !videoUrl.isEmpty()) {
            String youtubeEmbedUrl = videoUrl.replace("watch?v=", "embed/");
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(youtubeEmbedUrl);
        } else {
            webView.setVisibility(View.GONE); // Hide WebView if no video URL is available
        }
    }

    @Override
    public void showIngredients(List<Map<String, String>> ingredients) {
        ingredientsList.clear();
        ingredientsList.addAll(ingredients);
        Log.i(MEALDETAILSACTIVITY, "showIngredients: "  );
        adapter.notifyDataSetChanged();

    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}