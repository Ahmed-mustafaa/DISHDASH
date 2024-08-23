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
    import com.example.dishdash.db.AppData;
    import com.example.dishdash.view.Favorites.FavoritesActivity;
    import com.example.dishdash.view.SignUP.SignUpActivity;
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
         Button continueAsGuestButton;

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
            continueAsGuestButton = findViewById(R.id.continue_as_guest_button);
            mAuth = FirebaseAuth.getInstance();
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


            login.setOnClickListener(v -> {
                String Name = name.getText().toString();
                String Password = password.getText().toString();
                signInWithEmailPassword(Name, Password);

            });
            continueAsGuestButton.setOnClickListener(v -> {
                // Proceed as a guest
                Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                AppData.getInstance().setGuest(true);

                intent.putExtra("isGuest", true); // Pass a flag to indicate guest mode
                startActivity(intent);
                finish();
            });

            signLink = findViewById(R.id.sign_up_link);
            signLink.setOnClickListener(v -> {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            });

            logGoogle.setOnClickListener(v -> SignInWithGoogle());

        }

        private void SignInWithGoogle() {
            
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        private void signInWithEmailPassword(String email, String password) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String userId = user.getUid();
                                SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("user_id", userId);
                                editor.apply();
                                Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Toast.makeText(this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        private void clearPreviousSession() {
            SharedPreferences preferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear(); // Clear all stored data
            editor.apply();
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
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                String userId = user.getUid();
                                SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("user_id", userId);
                                editor.apply();
                                Toast.makeText(this, "Authentication Successful.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            Toast.makeText(this, "Authentication Successful.", Toast.LENGTH_SHORT).show();
                            // Redirect to MainActivity or another activity
                           /* Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                            startActivity(intent);
                            finish();*/
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }





        }

        
