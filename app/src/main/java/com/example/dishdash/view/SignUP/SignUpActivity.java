package com.example.dishdash.view.SignUP;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import com.example.dishdash.R;
import com.example.dishdash.view.DashBoardActivity;
import com.example.dishdash.view.LoginActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity  implements signUpView{
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
        presenter = new signUpPresenter(this);

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

            if (presenter.checkValidation(Name, Email, Password)) {
                signUp(Name, Email, Password);
            }
        });



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
    private void signUp(String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign-up successful
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {

                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            Map<String, Object> userMap = new HashMap<>();
                            userMap.put("name", name);
                            userMap.put("email", email);

                            db.collection("users").document(user.getUid())
                                    .set(userMap);
                            user.updateProfile(new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build());
                        }
                        showSignUpSuccess();
                    } else {
                        // Sign-up failed
                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                        showSignUpFailure(errorMessage);
                    }
                });
    }

  /*  public void SaveUserData(String  name, String email, String password){
        SharedPreferences sharedPreferences = getSharedPreferences("userData",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();

    }*/

    @Override
    public void showSignUpSuccess() {
        Snackbar.make(findViewById(android.R.id.content), "Sign Up Successful", Snackbar.LENGTH_SHORT).show();
        Intent intent = new Intent(SignUpActivity.this, DashBoardActivity.class);
        startActivity(intent);
        finish();
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