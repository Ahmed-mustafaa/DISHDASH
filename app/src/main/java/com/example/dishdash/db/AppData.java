package com.example.dishdash.db;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import com.example.dishdash.view.SignUP_LogIn.LoginActivity;

public class AppData {
    private static AppData instance;
    private boolean isGuest;
    private Context context; // Add a context field
    boolean isNetworkAvailable = true;

    public boolean isNetworkAvailable() {
        return isNetworkAvailable;
    }

    public void setNetworkAvailable(boolean networkAvailable) {
        isNetworkAvailable = networkAvailable;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId; // Store user ID

    public static void setInstance(AppData instance) {
        AppData.instance = instance;
    }

    private AppData() {}

    public static synchronized AppData getInstance() {
        if (instance == null) {
            instance = new AppData();
        }
        return instance;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean isGuest) {
        this.isGuest = isGuest;
    }
    public void initialize(Context context) {
        this.context = context; // Initialize the context
    }



    public void showLoginPrompt() {
        if (context == null) {
            throw new IllegalStateException("Context is not initialized. Call initialize() before using this method.");
        }

        new AlertDialog.Builder(context)
                .setTitle("Login Required")
                .setMessage("You need to log in to access favorites. Do you want to log in or continue browsing?")
                .setPositiveButton("Log In", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("Continue Browsing", null)
                .show();
    }
}

