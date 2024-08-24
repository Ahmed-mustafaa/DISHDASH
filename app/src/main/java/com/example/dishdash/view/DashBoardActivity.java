package com.example.dishdash.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dishdash.NetworkCall.NetworkListener;
import com.example.dishdash.db.AppData;
import com.example.dishdash.db.AppDataBase;
import com.example.dishdash.db.MealDAO;
import com.example.dishdash.view.Favorites.FavoritesActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import com.example.dishdash.view.SearchAvtivity.SearchActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DashBoardActivity extends AppCompatActivity implements MealsView {
    private ImageView avatarImageView;
    private TextView mealName;
    private SharedPreferences sharedPreferences;

    private TextView mealDescription;
    private TextView greeting;
    private TextView howIsItGoing;
    private TextView todaysMeals;
    private ImageView mealImage;
    private MealsPresenter presenter;
    private CardView cardView;
    private LottieAnimationView progress;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private CountriesAdapter dAdapter;
    private CountriesAdapter CategoriesAdapter;
    private FloatingActionButton fab;
    private FloatingActionButton fabLogout;
    private NetworkListener networkListener;

    String userId;
    private LottieAnimationView fabFavorites;
    private LottieAnimationView create;
    private boolean isFabOpen = false;
    private boolean isGuest;
    private LottieAnimationView SearchIcon;
    private List<DisplayItem> countryitems = new ArrayList<>();
    private List<DisplayItem> categoryitems = new ArrayList<>();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (networkListener != null) {
            unregisterReceiver(networkListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkListener = new NetworkListener();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkListener, filter);
        Intent intent = getIntent();
        isGuest = intent.getBooleanExtra("IS_GUEST", true);        setContentView(R.layout.activity_dash_board);
        sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        userId = sharedPreferences.getString("user_id", null);
        SearchIcon = findViewById(R.id.search_icon);
        SearchIcon.setOnClickListener(v -> {
            Intent Searchintent = new Intent(DashBoardActivity.this, SearchActivity.class);
            startActivity(Searchintent);
        });

        fabLogout = findViewById(R.id.fab_log_out);
        create=findViewById(R.id.home_icon);
        fabFavorites = findViewById(R.id.heart_icon); // Initialize the Favorites button
        Animation scaleUp = AnimationUtils.loadAnimation(this, R.anim.scaleup);
        LinearLayout FloatingActionButtons = findViewById(R.id.Linear);

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
        dAdapter = new CountriesAdapter(DashBoardActivity.this, countryitems);
        CategoriesAdapter = new CountriesAdapter(DashBoardActivity.this, categoryitems);
        recyclerView.setAdapter(dAdapter);
        recyclerView2.setAdapter(CategoriesAdapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String displayName = user.getDisplayName();
            if (displayName != null) {
                greeting.setText("Hello, " + displayName);

            } else {
                greeting.setText("Hello,");
            }
        } else {
            greeting.setText("Hello,");
        }
        new Handler().postDelayed(() -> {
            howIsItGoing.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView2.setVisibility(View.VISIBLE);
        }, 6000);

        progress = findViewById(R.id.lottieProgress);
        cardView = findViewById(R.id.user_greeting_card);
        todaysMeals = findViewById(R.id.todays_meals);
        avatarImageView = findViewById(R.id.imageView2);
        mealName = findViewById(R.id.meal_of_day_title);
        mealImage = findViewById(R.id.mealimage);
        mealDescription = findViewById(R.id.meal_of_day_description);
        avatarImageView.setVisibility(View.GONE);
        cardView.setVisibility(View.GONE);
        mealName.setVisibility(View.GONE);
        mealImage.setVisibility(View.GONE);
        mealDescription.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        cardView.setVisibility(View.GONE);
        todaysMeals.setVisibility(View.GONE);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            progress.setVisibility(View.GONE);
            cardView.setVisibility(View.GONE);
            avatarImageView.setVisibility(View.VISIBLE);
            mealName.setVisibility(View.VISIBLE);
            mealImage.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.VISIBLE);
            todaysMeals.setVisibility(View.VISIBLE);
        }, 6000);
        fabLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            clearSession();
            clearLocalData();
            Intent logOutintent = new Intent(DashBoardActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(logOutintent);

            finish();
        });
        create.setOnClickListener(v -> {
            AppData.getInstance().initialize(this);
            String userId = AppData.getInstance().getUserId();
            Intent planintent = new Intent(DashBoardActivity.this, CreatePlanActivity.class);
            startActivity(planintent);
                });




        fabFavorites.setOnClickListener(v -> {
            AppData.getInstance().initialize(this);
            if (AppData.getInstance().isGuest()) {
                showLoginPrompt();
                fabFavorites.setVisibility(View.INVISIBLE); // Hide favorites icon for guests
            } else {
                Intent Favintent = new Intent(DashBoardActivity.this, FavoritesActivity.class);
                startActivity(Favintent);
            }
        });


        mealImage.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mealImage.setAlpha(0.5f);
                    mealDescription.setVisibility(View.VISIBLE);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    mealImage.setAlpha(1.0f);
                    mealDescription.setVisibility(View.GONE);
                    break;
            }
            return true;
        });

        presenter = new MealsPresenter(this, new MealsRemoteDataSourceImpl());
        presenter.getRandom();

        MealsRemoteDataSourceImpl.getInstance().getCountries(new CountryCallBack() {
            @Override
            public void onSuccess(List<Country> countries) {
                runOnUiThread(() -> showAllCountries(countries));
            }

            @Override
            public void onCountryMealsSuccess(List<Meal> meals) {}

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(DashBoardActivity.this, "Failed to load countries", Toast.LENGTH_SHORT).show();
            }
        });

        MealsRemoteDataSourceImpl.getInstance().getCategories(new NetworkCallBack() {
            @Override
            public void onSuccess(Meal meal) {}

            @Override
            public void onSuccess(List<Meal> meal) {}

            @Override
            public void onSuccessCategory(List<Category> categories) {
                runOnUiThread(() -> showAllCategories(categories));
            }

            @Override
            public void onIngredientsSuccess(List<Map<String, String>> ingredients) {}

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(DashBoardActivity.this, "Failed to load categories", Toast.LENGTH_SHORT).show();
            }
        });

        loadAvatarImage();
    }
    private void showLoginPrompt() {
        new AlertDialog.Builder(this)
                .setTitle("Login Required")
                .setMessage("You need to log in to access favorites. Do you want to log in or continue browsing?")
                .setPositiveButton("Log In", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Continue Browsing", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing and dismiss the dialog
                        dialog.dismiss();
                    }
                })
                .setCancelable(true)
                .show();
    }
    private void clearSession() {
       SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear(); // Clear all stored data
        editor.apply();

        FirebaseAuth.getInstance().signOut();

   }
    private void clearLocalData() {
        AppDataBase db = AppDataBase.getInstance(this);
        MealDAO dao = db.getProductDAO();

        // Assuming userID is stored and accessible
        String userID = getSharedPreferences("app_prefs", MODE_PRIVATE).getString("user_id", null);

        if (userID != null) {
            new Thread(() -> dao.deleteMealsByUserId(userID)).start();
        }
    }

    public void loadAvatarImage() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://dishdash-aadbb.appspot.com/7a7326af-b921-49d8-9a14-c713167b2721.jpg");
            storageReference.getDownloadUrl().addOnSuccessListener(uri -> Glide.with(DashBoardActivity.this)
                            .load(uri)
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .circleCrop()
                            .into(avatarImageView))
                    .addOnFailureListener(exception -> Toast.makeText(DashBoardActivity.this, "Failed to load avatar", Toast.LENGTH_SHORT).show());
        }
    }

    @Override
    public void showRandomMeal(Meal meal) {
        mealName.setText(meal.getStrMeal());
        String[] description = {meal.getStrCategory(), meal.getStrArea()};
        mealDescription.setText(String.join(", ", description));
        Glide.with(this).load(meal.getStrMealThumb()).into(mealImage);
    }

    @Override
    public void showDailyMeals(List<Meal> meals) {
        dAdapter.notifyDataSetChanged();
    }

    @Override
    public void showFABOptions() {
        fab.show();

    }

    @Override
    public void hideFABOptions() {
        fab.hide();
    }

    @Override
    public void showAllCountries(List<Country> countries) {
        countryitems.addAll(countries.stream()
                .map(country -> new DisplayItem(DisplayItem.ItemType.COUNTRY, country.getStrArea(), null))
                .collect(Collectors.toList()));
        dAdapter.setItems(countryitems);
    }
    private void logout() {
        // Clear the user ID from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("user_id");
        editor.apply();

        // Sign out from Firebase
        FirebaseAuth.getInstance().signOut();

        // Redirect to the login screen or another appropriate screen
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
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
