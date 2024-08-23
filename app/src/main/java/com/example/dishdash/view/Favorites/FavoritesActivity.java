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
    String userID;
    MealDAO dao;

    RecyclerView FavRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fav);

        userID = getIntent().getStringExtra("user_id");

        FavRecyclerView = findViewById(R.id.Favres);
        FavRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        FavRecyclerView.setLayoutManager(layoutManager);
        Favproducts = new ArrayList<>();
        favadapter = new favAdapter( this,this);
        FavRecyclerView.setAdapter(favadapter);
        presenter =  new FavoritesPresenter( this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(),
                MealsLocalDataSourceImpl.getInstance(this)));
        if (userID != null) {
            presenter.getFavoriteMeals(userID);
        } else {
            showEmptyFavoritesMessage();
        }

        AppDataBase db = AppDataBase.getInstance(this);
        dao = db.getProductDAO();
        dao.getFavoriteMeals();
        Observer<List<Meal>> observer = new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> Favproducts) {
                favadapter.setList(Favproducts);
                Log.i(TAG, "onChanged: " + Favproducts.size() + "Items in DB`");

            }
        };
        LiveData<List<Meal>> observable = dao.getFavoriteMeals();
        observable.observe(this, observer);

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
        if (meals != null) {
            favadapter.setList(meals);
        }else {
            showEmptyFavoritesMessage();
        }
    }

    @Override
    public void showEmptyFavoritesMessage() {
        Toast.makeText(this, "No favorite products", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdd(Meal meal) {

    }
}