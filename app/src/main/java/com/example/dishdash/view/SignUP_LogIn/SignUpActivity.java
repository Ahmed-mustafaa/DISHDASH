package com.example.dishdash.view.SignUP_LogIn;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dishdash.presenter.signUpPresenter;
import com.google.firebase.firestore.FirebaseFirestore;

import com.example.dishdash.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity  implements RegisteringUserContract {
    TextInputEditText name, email, password;
    private TextInputLayout nameLayout, emailLayout, passwordLayout;
    private TextView loginLink;
    signUpPresenter presenter;

    Button  signup;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup_button);
        nameLayout = findViewById(R.id.name_layout);
        emailLayout = findViewById(R.id.email_layout);
        passwordLayout = findViewById(R.id.password_layout);
        loginLink = findViewById(R.id.Login_link);
        presenter = new signUpPresenter(this);

        loginLink.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
                });
                    name.selectAll();
                    name.isTextSelectable();

        signup.setOnClickListener(v -> {
            String Email = String.valueOf(email.getText()).trim();
            String Password = String.valueOf(password.getText()).trim();
            String Name = String.valueOf(name.getText()).trim();
            signUp(Name, Email, Password);

        });



    }

    public void signUp(String Name , String email, String password) {
        if (presenter.checkValidation(Name, email, password)){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                //Log.i(TAG, "signUp: ");
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                Map<String, Object> userMap = new HashMap<>();
                                userMap.put("name", Name);
                                userMap.put("email", email);

                                db.collection("users").document(user.getUid())
                                        .set(userMap).addOnSuccessListener(aVoid -> {
                                    user.updateProfile(new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(Name)
                                                    .build())
                                            .addOnCompleteListener(profileTask -> {
                                                if (profileTask.isSuccessful()) {
                                                    showSignUpSuccess();
                                                } else {
                                                    showSignUpFailure("Profile update failed: " + profileTask.getException().getMessage());
                                                }
                                            });
                                })
                                        .addOnFailureListener(e -> showSignUpFailure("Database update failed: " + e.getMessage()));
                            }
                        } else {
                            // Sign-up failed
                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                            showSignUpFailure(errorMessage);
                        }
                    });
        }
    }

    @Override
    public void signIn() {

    }


    @Override
    public void showSignUpSuccess() {
        Snackbar.make(findViewById(android.R.id.content), "Sign Up Successful", Snackbar.LENGTH_SHORT).show();
//        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
//        startActivity(intent);
//        finish();
    }

    @Override
    public void showSignUpFailure(String error) {
        Toast.makeText(this, "Sign Up Failed: " + error, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showValidationError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();

    }
}