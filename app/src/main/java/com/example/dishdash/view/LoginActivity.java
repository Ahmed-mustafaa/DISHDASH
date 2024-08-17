    package com.example.dishdash.view;

    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.util.Log;
    import android.widget.Button;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;

    import com.example.dishdash.R;
    import com.google.android.gms.auth.api.signin.GoogleSignIn;
    import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
    import com.google.android.gms.auth.api.signin.GoogleSignInClient;
    import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
    import com.google.android.gms.common.api.ApiException;
    import com.google.android.gms.tasks.Task;
    import com.google.android.material.textfield.TextInputEditText;
    import com.google.android.material.textfield.TextInputLayout;
    import com.google.firebase.auth.AuthCredential;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.auth.GoogleAuthProvider;


    public class LoginActivity extends AppCompatActivity {
        private static final int RC_SIGN_IN =9001 ;
        private TextView signLink;
        TextInputEditText name, password;

        private TextInputLayout nameLayout, passwordLayout;
        Button login;
        Button logGoogle;
        private GoogleSignInClient mGoogleSignInClient;
        private FirebaseAuth mAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_login);
            name = findViewById(R.id.name);
            password = findViewById(R.id.password);
            nameLayout = findViewById(R.id.name_layout);
            passwordLayout = findViewById(R.id.password_layout);
            login = findViewById(R.id.login_button);
            logGoogle = findViewById(R.id.google_sign_in_button);
            mAuth = FirebaseAuth.getInstance();
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


            login.setOnClickListener(v -> {
                String Name = name.getText().toString();
                String Password = password.getText().toString();
                validate(Name, Password);

            });


            signLink = findViewById(R.id.sign_up_link);
            signLink.setOnClickListener(v -> {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            });

            logGoogle.setOnClickListener(v -> SignInWithGoogle());

//       /* boolean validate(String name , String password){
//
//            SharedPreferences sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);
//            String storedName = sharedPreferences.getString("name",null);
//            String storedPassword = sharedPreferences.getString("password",null);
//
//            if(storedName != null && storedPassword != null && storedName.equals(name) && storedPassword.equals(password)) {
//               *//* Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();*//*
//
//                Toast.makeText(this, "Welcome back", Toast.LENGTH_SHORT).show();
//                return true;
//            }else {
//                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//
//        }
        }



        private void SignInWithGoogle() {
            
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign-In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    // Google Sign-In failed
                    Log.w("GoogleSignIn", "Google sign in failed", e);
                    Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show();
                }
            }
        }

        private void firebaseAuthWithGoogle(String idToken) {
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(this, "Authentication Successful.", Toast.LENGTH_SHORT).show();
                            // Redirect to MainActivity or another activity
                            Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        boolean validate(String name, String password) {
            SharedPreferences sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);
            String storedName = sharedPreferences.getString("name", null);
            String storedPassword = sharedPreferences.getString("password", null);

            if (storedName != null && storedPassword != null && storedName.equals(name) && storedPassword.equals(password)) {
                Toast.makeText(this, "Welcome back", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }
        
