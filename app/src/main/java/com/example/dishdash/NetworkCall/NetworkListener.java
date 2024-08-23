package com.example.dishdash.NetworkCall;

import android.content.BroadcastReceiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

    public class NetworkListener extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!isNetworkAvailable(context)) {
                showNoNetworkDialog(context);
            }
        }

        private boolean isNetworkAvailable(Context context) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }

        private void showNoNetworkDialog(Context context) {
            new AlertDialog.Builder(context)
                    .setTitle("No Network Connection")
                    .setMessage("You are currently not connected to the internet. Please check your connection.")
                    .setPositiveButton("OK", (dialog, which) -> {
                        // Handle OK button click
                        dialog.dismiss();

                        // Here you can call a method to synchronize cached meals or any other action
                        synchronizeCachedMeals(context);
                    })
                    .show();
        }

        private void synchronizeCachedMeals(Context context) {
            // Implement synchronization of cached meals here
            // For example, use a local database or cache to synchronize meals
            Toast.makeText(context, "Synchronizing cached meals...", Toast.LENGTH_SHORT).show();
            // You can use your existing logic to sync cached meals
        }
    }
