package com.example.dishdash.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.dishdash.NetworkCall.MealsRemoteDataSourceImpl;
import com.example.dishdash.R;
import com.example.dishdash.model.Meal;
import com.example.dishdash.presenter.MealsPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DashBoardActivity extends AppCompatActivity implements MealsView {
    private ImageView avatarImageView;
    private TextView mealName;
    private TextView mealDescription;
    private ImageView mealImage;
    private MealsPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        avatarImageView = findViewById(R.id.imageView2);
        mealName = findViewById(R.id.meal_of_day_title);
        mealImage = findViewById(R.id.meal_image);
        mealDescription = findViewById(R.id.meal_of_day_description);

        presenter = new MealsPresenter(this, new MealsRemoteDataSourceImpl());
        presenter.getRandom();

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
        Glide.with(this).load(meal.getStrMealThumb()).into(mealImage);
    }


    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }
}