package com.example.dishdash.NetworkCall;

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
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    private void showNoNetworkDialog(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("No Internet Connection")
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

   /* private void showchashedFavorite(Context context) {
        FavDAO dao = AppDataBase.getInstance(context).getFavDAO();

        String userId = AppData.getInstance().getUserId();
        LiveData<List<Meal>> favoriteMeals = dao.getFavoritesByUserId(userId);

        // 3. Observe the LiveData
        favoriteMeals.observe(this, meals -> {
            if (meals != null && !meals.isEmpty()) {
                // 4. Display favorite meals in a RecyclerView or other UI element
                // ... (Set up RecyclerView and adapter)
            } else {
                // 5. Show a message if no favorites are found
                Toast.makeText(context, "No favorite meals found", Toast.LENGTH_SHORT).show();
            }
        });

    }*/
}
