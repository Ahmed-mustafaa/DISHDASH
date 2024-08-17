package com.example.dishdash.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.dishdash.R;

public class SplashScreen extends AppCompatActivity {
LottieAnimationView lottie;
    TextView dishDashText ;
    TextView mealMateText;
    private ImageView greenGrass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen2);
        lottie = findViewById(R.id.lottieAnimationView);
        dishDashText= findViewById(R.id.dishdash_text); // assuming you have set an id in XML
        mealMateText  = findViewById(R.id.meal_mate_text);
        greenGrass = findViewById(R.id.vectorImageView);

        // Create the Translate Animation
        TranslateAnimation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, -1.0f, // Start from left off-screen
                Animation.RELATIVE_TO_SELF, 0.0f,  // End at original position
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f
        );
        AlphaAnimation alpha = new AlphaAnimation(0.0f, 1.0f);
        alpha.setDuration(1000); // 1 second

        // Combine both animations in an AnimationSet
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(translate);
        animationSet.addAnimation(alpha);
        animationSet.setDuration(1000); // Set the duration for the entire set

// Start the animation
        dishDashText.startAnimation(animationSet);

   //     lottie.animate().translationY(-1600).setDuration(3000).setStartDelay(0);


        String fullText = "Your Meal Mate";


// Create a Handler to update the text
        Handler handler = new Handler();
        int delay = 150; // delay in milliseconds before adding next character

        for (int i = 0; i < fullText.length(); i++) {
            final int index = i;
            handler.postDelayed(() -> {
                mealMateText.setText(fullText.substring(0, index + 1));
            }, delay * i);
        }
        new Handler().postDelayed(() -> {
            // Animate the appearance of the vector image
            greenGrass.setVisibility(View.VISIBLE);
            Animation fadeIn = new AlphaAnimation(0, 1);
            fadeIn.setDuration(1000); // Duration of the fade-in animation
            greenGrass.startAnimation(fadeIn);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, SignUpActivity.class);
                    startActivity(intent);
                    finish();
                }
                    },1000);
            }, 1000);
        }
    }
