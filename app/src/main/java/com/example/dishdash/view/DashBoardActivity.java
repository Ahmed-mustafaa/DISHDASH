package com.example.dishdash.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.dishdash.NetworkCall.DailyMealsCall;
import com.example.dishdash.NetworkCall.MealsRemoteDataSourceImpl;
import com.example.dishdash.R;
import com.example.dishdash.model.Meal;
import com.example.dishdash.presenter.MealsPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DashBoardActivity extends AppCompatActivity implements MealsView {
    private ImageView avatarImageView;
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
    DailyMealsAdapter dAdapter;
    View categories;
    View countries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        greeting = findViewById(R.id.user_greeting);
        howIsItGoing = findViewById(R.id.HowIsItGOing);
        recyclerView = findViewById(R.id.meals_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        dAdapter = new DailyMealsAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(dAdapter);

        recyclerView.setVisibility(View.GONE);
        howIsItGoing.setVisibility(View.GONE);
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
                    }
                }, 6000);
            } else {
                greeting.setText("Hello, Guest");
            }
        } else {
            greeting.setText("Hello, Guest");
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
                findViewById(R.id.view).setVisibility(View.VISIBLE);


            }
        }, 6000);

        categories.setOnClickListener(v->{
            categories.setVisibility(View.VISIBLE);
         //   startActivity(new Intent(DashBoardActivity.this,CategoriesActivity.class));
        });
        countries.setOnClickListener(v->{
            countries.setVisibility(View.VISIBLE);
          //  startActivity(new Intent(DashBoardActivity.this,CountriesActivity.class));


        });

        ;countries.setOnTouchListener((v, event) ->{
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            countries.setVisibility(View.VISIBLE);  // Show the description
                            break;

                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            countries.setVisibility(View.GONE);  // Hide the description
                            break;
                    }
                    return true;

                });
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
        MealsRemoteDataSourceImpl.getInstance().getDailyMeals(new DailyMealsCall() {
            @Override
            public void OnSuccess(List<Meal> meals) {
                dAdapter.setMeals(meals);
                dAdapter.notifyDataSetChanged();
            }

            @Override
            public void OnFailure(Throwable throwable) {

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
        dAdapter.setMeals(meals);
        dAdapter.notifyDataSetChanged();
    }


    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }


}