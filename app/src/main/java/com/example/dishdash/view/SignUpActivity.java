package com.example.dishdash.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dishdash.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {
    TextInputEditText name, email, password;
    private TextInputLayout nameLayout, emailLayout, passwordLayout;
    private TextView loginLink;

    Button  signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);
        SharedPreferences userData = getSharedPreferences("userData", MODE_PRIVATE);
        SharedPreferences.Editor editor = userData.edit();
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup_button);
        nameLayout = findViewById(R.id.name_layout);
        emailLayout = findViewById(R.id.email_layout);
        passwordLayout = findViewById(R.id.password_layout);
        loginLink = findViewById(R.id.Login_link);
        loginLink.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
                });
       name.selectAll();
       name.isTextSelectable();

        signup.setOnClickListener(v -> {
            String Email = email.getText().toString().trim();
            String Password = password.getText().toString().trim();
            String Name = name.getText().toString().trim();
            if (checkValidation(Name, Email, Password)) {
                if (!AccountIsExisted(Email)) {
                    new AlertDialog.Builder(SignUpActivity.this)
                            .setTitle("Email Already Registered")
                            .setMessage("This email is already registered. Please log in instead.")
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                } else {
                    SaveUserData(Name, Email, Password);
                    Snackbar.make(v, "Sign Up Successful", Snackbar.LENGTH_SHORT).show();
                }
            }
        });



    }

    public boolean checkValidation(String name, String email, String password) {
        if (name.isEmpty()) {
            nameLayout.setError("Name is required");
            Toast.makeText(this, "Name is required ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty()) {
            passwordLayout.setError("Password is required");
            Toast.makeText(this, "Password is required ", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(password.length() < 8 ||!password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*[0-9].*")){
            passwordLayout.setError("password must be more than 8 characters containing numbers, UPPERCASE and lowercase letters");
        }
        if (email.isEmpty()) {
            emailLayout.setError("Email is required");
            Toast.makeText(this, "Email is required ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!email.contains("@")) {
            emailLayout.setError("Invalid email");
            Toast.makeText(this, "Invalid email ", Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
    }
   private boolean AccountIsExisted(String email){
        SharedPreferences sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);
        String storedEmail = sharedPreferences.getString("email", null);
        if(storedEmail == null){
            return true;
        }
        else if (!storedEmail.equals(email)){
            return true;
        }
         return false;
    }

    public void SaveUserData(String  name, String email, String password){
        SharedPreferences sharedPreferences = getSharedPreferences("userData",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();

    }
}