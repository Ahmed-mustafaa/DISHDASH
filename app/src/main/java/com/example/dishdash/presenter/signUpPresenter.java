package com.example.dishdash.presenter;

import com.example.dishdash.view.SignUP_LogIn.RegisterationModel;
import com.example.dishdash.view.SignUP_LogIn.RegisteringUserContract;
import com.google.firebase.auth.FirebaseAuth;

public class signUpPresenter  {
    private RegisteringUserContract view;
    private FirebaseAuth mAuth;
    RegisterationModel Reg;

    public signUpPresenter(RegisteringUserContract view) {
        this.view = view;
        Reg = new RegisterationModel();
    }

    public boolean checkValidation(String name, String email, String password){
        try {
        Reg.ValidateUser(name, email, password);
            view.showSignUpSuccess();
            return true;
        }catch (Exception e){
            view.showSignUpFailure(e.getMessage());
            return  false;
        }

    }


}