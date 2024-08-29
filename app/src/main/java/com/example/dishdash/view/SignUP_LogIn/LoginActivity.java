    package com.example.dishdash.view.SignUP_LogIn;

    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.widget.Button;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;

    import com.example.dishdash.R;
    import com.example.dishdash.db.AppData;
    import com.example.dishdash.view.DashBoard.DashBoardActivity;
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
        TextInputEditText name, password ,email;

        private TextInputLayout nameLayout, passwordLayout;
        Button login;
        Button logGoogle;
         Button continueAsGuestButton;

        private GoogleSignInClient mGoogleSignInClient;
        private FirebaseAuth mAuth;
        private static final String TAG = "LoginActivity";

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
                String email = name.getText().toString();
                String Password = password.getText().toString();
                Log.i(TAG, "name and password: " + email + Password);
                signInWithEmailPassword(email, Password);


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
            email = email.trim();
            password = password.trim();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            try {
                                String userId = user.getUid();
                                AppData.getInstance().setUserId(userId);
                                AppData.getInstance().setGuest(false);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            updateUI(user);

                            if (user != null) {
                                Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Toast.makeText(this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

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
                                AppData.getInstance().setUserId(userId);
                                AppData.getInstance().setGuest(false);

                                Log.i(TAG, "firebaseAuthWithGoogle: "+userId );
                                Toast.makeText(this, "Authentication Successful.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            Toast.makeText(this, "Authentication Successful.", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        private void updateUI(FirebaseUser user) {
            if (user != null) {
                // User is signed in
                // Perform UI updates for signed-in users
                // Example: Hide sign-in button, show welcome message, etc.
                name.setText(user.getEmail()); // Set the email field with the user's email
                password.setText(null); // Clear the password field
                Toast.makeText(this, "Signed in as " + user.getEmail(), Toast.LENGTH_SHORT).show();
            } else {

                name.setText(null); // Clear the email field
                password.setText(null); // Clear the password field
                Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show();
            }
        }

    }

        
