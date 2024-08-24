package com.example.dishdash.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.example.dishdash.R;
import com.example.dishdash.view.SearchAvtivity.SearchActivity;

public class CreatePlanActivity extends AppCompatActivity {
    LottieAnimationView create;
    LottieAnimationView create1;
    LottieAnimationView create2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_plan);
        create = findViewById(R.id.add1);
        create1 = findViewById(R.id.add2);
        create2 = findViewById(R.id.add3);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create.playAnimation();
                Intent intent = new Intent(CreatePlanActivity.this, SearchActivity.class);
                startActivity(intent);

            }
        });create1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create.playAnimation();
                Intent intent = new Intent(CreatePlanActivity.this, SearchActivity.class);
                startActivity(intent);

            }
        });
        create2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create.playAnimation();
                Intent intent = new Intent(CreatePlanActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

    }
}