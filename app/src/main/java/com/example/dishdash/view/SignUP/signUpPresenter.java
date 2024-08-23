package com.example.dishdash.view.SignUP;

import static androidx.constraintlayout.motion.widget.TransitionBuilder.validate;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signUpPresenter {
    private signUpView view;
    private FirebaseAuth mAuth;

    public signUpPresenter(signUpView view) {
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
    }

    public boolean checkValidation(String name, String email, String password) {
        if (name.isEmpty()) {
            view.showValidationError("Name is required");
            return false;
        }
        if (email.isEmpty() || !email.contains("@")) {
            view.showValidationError("Valid email is required");
            return false;
        }
        if (password.isEmpty() || password.length() < 8 || !password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*[0-9].*")) {
            view.showValidationError("Password must be at least 8 characters long and contain numbers, uppercase and lowercase letters");
            return false;
        }
        return true;
    }

    public void signUp(String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        view.showSignUpSuccess();
                    } else {
                        view.showSignUpFailure(task.getException().getMessage());
                    }
                });
    }
}