package com.example.dishdash;

import static androidx.constraintlayout.motion.widget.TransitionBuilder.validate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class LoginActivity extends AppCompatActivity {
    private TextView signLink;
    TextInputEditText name, password;
    private TextInputLayout nameLayout, passwordLayout;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        nameLayout = findViewById(R.id.name_layout);
        passwordLayout = findViewById(R.id.password_layout);
        login = findViewById(R.id.login_button);

        login.setOnClickListener(v -> {
            String Name = name.getText().toString();
            String Password = password.getText().toString();
            validate(Name,Password);

        });


        signLink = findViewById(R.id.sign_up_link);
signLink.setOnClickListener(v -> {
    Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
    startActivity(intent);
});
    }
    boolean validate(String name , String password){

        SharedPreferences sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);
        String storedName = sharedPreferences.getString("name",null);
        String storedPassword = sharedPreferences.getString("password",null);

        if(storedName != null && storedPassword != null && storedName.equals(name) && storedPassword.equals(password)) {
           /* Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();*/

            Toast.makeText(this, "Welcome back", Toast.LENGTH_SHORT).show();
            return true;
        }else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}