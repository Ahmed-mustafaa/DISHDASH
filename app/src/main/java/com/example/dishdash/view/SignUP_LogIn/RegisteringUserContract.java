package com.example.dishdash.view.SignUP_LogIn;

public interface RegisteringUserContract {
        void showSignUpSuccess();
        void showSignUpFailure(String error);
        void showValidationError(String message);
        void signUp(String name, String email, String password);
        void signIn();


}
