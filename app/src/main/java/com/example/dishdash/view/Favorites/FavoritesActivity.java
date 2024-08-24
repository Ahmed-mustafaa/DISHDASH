package com.example.dishdash.view.Favorites;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


import androidx.appcompat.app.AppCompatActivity;


import com.example.dishdash.NetworkCall.MealsRemoteDataSourceImpl;
import com.example.dishdash.R;
import com.example.dishdash.db.*;
import com.example.dishdash.model.Meal;
import com.example.dishdash.db.AppDataBase;

import com.example.dishdash.model.Repository.MealsRepositoryImpl;
import com.example.dishdash.view.*;



public class FavoritesActivity extends AppCompatActivity implements onRClickListener,FavoritesView{
    favAdapter favadapter;
    public static final String TAG = "FavProductsActivity";
    MealsRemoteDataSourceImpl mealsRemoteDataSource;
    MealsLocalDataSource mealsLocalDataSource;
    LinearLayoutManager layoutManager;
    FavPresenter presenter;
    public List<Meal> Favproducts;

    RecyclerView FavRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fav);

        AppData.getInstance().initialize(this);
        FavRecyclerView = findViewById(R.id.Favres);
        FavRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        FavRecyclerView.setLayoutManager(layoutManager);
        Favproducts = new ArrayList<>();
        favadapter = new favAdapter( this,this);
        FavRecyclerView.setAdapter(favadapter);
        mealsLocalDataSource = MealsLocalDataSourceImpl.getInstance(this);

        presenter =  new FavoritesPresenter( this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(),
                MealsLocalDataSourceImpl.getInstance(this)));
        AppDataBase db = AppDataBase.getInstance(this);
        if (db == null) {
            Log.e(TAG, "onCreate: Database is null");
            return;
        }
        FavDAO dao = db.getFavDAO();

        if (dao == null) {
            Log.e(TAG, "onCreate: DAO instance is null");
        }

        String userID = AppData.getInstance().getUserId();
        Log.i(TAG, "userId : "+userID);
        if (userID == null || userID.isEmpty()) {
            Log.e(TAG, "UserID is null or empty");

            showEmptyFavoritesMessage(); // Show a message if no user ID is available
            return; // Stay on the screen without making a database call
        }

        LiveData<List<Meal>> observable = dao.getFavoritesByUserId(userID);
        if (observable == null) {
            Log.e(TAG, "onCreate: Observable is null");
            return;
        }

        observable.observe(this, favMeals -> {
            if (favMeals == null || favMeals.isEmpty()) {
                showEmptyFavoritesMessage();
            } else {
                favadapter.setList(favMeals);
                favadapter.notifyDataSetChanged();
                Log.i(TAG, "onChanged: " + favMeals.size() + " items in DB");
            }
        });
    }


    @Override
    public void onRemove(Meal meal) {
        meal.setFavorite(false);
        MealsRepositoryImpl.getInstance(mealsRemoteDataSource, mealsLocalDataSource);
        mealsLocalDataSource.deleteMeal(meal);
        Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setMeals(List<Meal> meals) {

    }

    @Override
    public void displayFavoriteMeals(List<Meal> meals) {
        if (meals != null && !meals.isEmpty()) {
            favadapter.setList(meals);
        } else {
            showEmptyFavoritesMessage();
        }
    }

    @Override
    public void showEmptyFavoritesMessage() {
        Toast.makeText(this, "No favorite Meals", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdd(Meal meal) {

    }
}