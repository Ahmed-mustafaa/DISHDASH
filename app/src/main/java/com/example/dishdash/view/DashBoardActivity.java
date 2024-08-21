package com.example.dishdash.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.dishdash.NetworkCall.CountryCallBack;
import com.example.dishdash.NetworkCall.MealsRemoteDataSourceImpl;
import com.example.dishdash.NetworkCall.NetworkCallBack;
import com.example.dishdash.R;
import com.example.dishdash.model.Category;
import com.example.dishdash.model.Country;
import com.example.dishdash.model.DisplayItem;
import com.example.dishdash.model.Meal;
import com.example.dishdash.presenter.MealsPresenter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DashBoardActivity extends AppCompatActivity implements MealsView {
    private ImageView avatarImageView;
    NavigationView navigationView;
    private TextView mealName;
    private TextView mealDescription;
    private TextView greeting;
    private TextView howIsItGoing;
    TextView todaysMeals;
    private ImageView mealImage;
    private MealsPresenter presenter;
    CardView cardView;
    LottieAnimationView progress;
    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    CountriesAdapter dAdapter;
    CountriesAdapter CategoriesAdapter;
    private List<DisplayItem> countryitems = new ArrayList<>(); // Declare items as a class member
    private List<DisplayItem> categoryitems = new ArrayList<>(); // Declare items as a class member

    View categories;
    View countries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        navigationView = findViewById(R.id.navigation);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);
        greeting = findViewById(R.id.user_greeting);
        howIsItGoing = findViewById(R.id.HowIsItGOing);
        recyclerView = findViewById(R.id.countries_recyclerview);
        recyclerView2 = findViewById(R.id.categories_recyclerview);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setVisibility(View.GONE);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setVisibility(View.GONE);
        howIsItGoing.setVisibility(View.GONE);
        dAdapter = new CountriesAdapter(DashBoardActivity.this, countryitems); // Create adapter instance with empty list
        CategoriesAdapter = new CountriesAdapter(DashBoardActivity.this, categoryitems); // Create adapter instance with empty list
        recyclerView.setAdapter(dAdapter);
        recyclerView2.setAdapter(CategoriesAdapter);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String displayName = user.getDisplayName();
            if (displayName != null) {
                greeting.setText("Hello, " + displayName);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        howIsItGoing.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView2.setVisibility(View.VISIBLE);
                    }
                }, 6000);
            } else {
                greeting.setText("Hello,");
            }
        } else {
            greeting.setText("Hello,");
        }
        progress = findViewById(R.id.lottieProgress);
        cardView = findViewById(R.id.user_greeting_card);
        todaysMeals = findViewById(R.id.todays_meals);
        avatarImageView = findViewById(R.id.imageView2);
        mealName = findViewById(R.id.meal_of_day_title);
        mealImage = findViewById(R.id.mealimage);
        mealDescription = findViewById(R.id.meal_of_day_description);
        avatarImageView.setVisibility(View.GONE);
        findViewById(R.id.MealOfTheDayCard);
        cardView.setVisibility(View.GONE);
        mealName.setVisibility(View.GONE);
        mealImage.setVisibility(View.GONE);
        mealDescription.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.GONE);
        categories =  findViewById(R.id.view);
        categories.setVisibility(View.GONE);
        countries = findViewById(R.id.view2);
        countries.setVisibility(View.GONE);
        todaysMeals.setVisibility(View.GONE);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progress.setVisibility(View.GONE);
                cardView.setVisibility(View.GONE);
                avatarImageView.setVisibility(View.VISIBLE);
                mealName.setVisibility(View.VISIBLE);
                mealImage.setVisibility(View.VISIBLE);
                cardView.setVisibility(View.VISIBLE);
                todaysMeals.setVisibility(View.VISIBLE);
             /*   findViewById(R.id.view).setVisibility(View.VISIBLE);
                findViewById(R.id.view2).setVisibility(View.VISIBLE);*/


            }
        }, 6000);

        mealImage.setOnTouchListener( (v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mealImage.setAlpha(0.5f);  // Dim the image when touched
                    mealDescription.setVisibility(View.VISIBLE);  // Show the description
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    mealImage.setAlpha(1.0f);  // Restore image opacity
                    mealDescription.setVisibility(View.GONE);  // Hide the description
                    break;
            }
            return true;
        });
        presenter = new MealsPresenter(this, new MealsRemoteDataSourceImpl());
        presenter.getRandom();
        MealsRemoteDataSourceImpl.getInstance().getCountries(new CountryCallBack() {
            @Override
            public void onSuccess(List<Country> countries) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        showAllCountries(countries);
                    }
                });
            }

            @Override
            public void onCountryMealsSuccess(List<Meal> meals) {

            }


            @Override
            public void onFailure(Throwable throwable) {
Toast.makeText(DashBoardActivity.this, "Failed to load countries", Toast.LENGTH_SHORT).show();            }


        });
        MealsRemoteDataSourceImpl.getInstance().getCategories(new NetworkCallBack() {
            @Override
            public void onSuccess(Meal meal) {

            }

            @Override
            public void onSuccessCategory(List<Category> categories) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        showAllCategories(categories);
                    }
            });
            }

            @Override
            public void onFailure(Throwable t) {
Toast.makeText(DashBoardActivity.this, "Failed to load categories", Toast.LENGTH_SHORT).show();
                 new Throwable("Error fetching Categories");
            }
        });


                loadAvatarImage();

    }

    private void loadAvatarImage() {
        // Assume that the user's avatar is stored in Firebase Storage under "avatars/userId.jpg"
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://dishdash-aadbb.appspot.com/7a7326af-b921-49d8-9a14-c713167b2721.jpg");

            // Get the download URL
            storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                // Load the image into the ImageView using Glide
                Glide.with(DashBoardActivity.this)
                        .load(uri)
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .circleCrop()
                        .into(avatarImageView);
            }).addOnFailureListener(exception -> {
                // Handle any errors
                Toast.makeText(DashBoardActivity.this, "Failed to load avatar", Toast.LENGTH_SHORT).show();
            });
        }
    }


    @Override
    public void showRandomMeal(Meal meal) {
        mealName.setText(meal.getStrMeal());
        String[] description = {meal.getStrCategory(),meal.getStrArea()};
        mealDescription.setText(String.join(", ", description));

        Glide.with(this).load(meal.getStrMealThumb()).into(mealImage);
    }

    @Override
    public void showDailyMeals(List<Meal> meals) {
        /*dAdapter.setMeals(meals);*/
        dAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAllCountries(List<Country> countries) {
        countryitems.addAll(countries.stream()
                .map(country -> new DisplayItem(DisplayItem.ItemType.COUNTRY, country.getStrArea(), null))
                .collect(Collectors.toList()));
        dAdapter.setItems(countryitems);

    }


    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showAllCategories(List<Category> categories) {
        categoryitems.addAll(categories.stream()
                .map(category -> new DisplayItem(DisplayItem.ItemType.CATEGORY,
                        category.getStrCategory(), category.getStrCategoryThumb()))
                .collect(Collectors.toList()));
        CategoriesAdapter.setItems(categoryitems);
        recyclerView2.setAdapter(CategoriesAdapter);
    }


}
/* navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.categories:
                    navController.navigate(R.id.categoriesFragment);
                    break;
                case R.id.countries:
                    navController.navigate(R.id.countriesFragment);
                    break;
            }
            DrawerLayout drawer = findViewById(R.id.drawer_layout); // Assuming you have a DrawerLayout with this ID
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });*/