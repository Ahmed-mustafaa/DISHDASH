package com.example.dishdash.model.Repository;

import androidx.lifecycle.LiveData;

import com.example.dishdash.db.FavDAO;
import com.example.dishdash.db.MealDAO;
import com.example.dishdash.db.MealsLocalDataSource;
import com.example.dishdash.NetworkCall.NetworkCallBack;
import com.example.dishdash.NetworkCall.MealsRemoteDataSourceImpl;
import com.example.dishdash.model.Meal;

import java.util.List;

public class MealsRepositoryImpl implements MealsRepository {

    MealsRemoteDataSourceImpl remote;
    MealsLocalDataSource local;
    FavDAO FavDao; // Reference to ProductDAO

    private  static MealsRepositoryImpl repo=null;
    private MealsRepositoryImpl(MealsRemoteDataSourceImpl remote , MealsLocalDataSource local){
   this.remote=remote;
   this.local=local;
   this.FavDao=local.favDAO();
    }

    public static MealsRepositoryImpl getInstance(MealsRemoteDataSourceImpl remote , MealsLocalDataSource local){
        if(repo==null){
            repo=new MealsRepositoryImpl(remote,local);
        }
        return repo;
    }

    @Override
    public void insertMeal(Meal meal,String userId) {
        meal.setFavorite(true);
        local.insertMeal(meal);
    }

    @Override
    public void deleteMeal(Meal meal) {
        local.deleteMeal(meal);
    }

    @Override
    public LiveData<List<Meal>> getAllMeals() {
        return local.getSortedMeals();
    }

    @Override
    public void getAllMeals(NetworkCallBack networkCallBacks) {
     remote.makeNetworkCall(networkCallBacks);
    }

    @Override
    public LiveData<List<Meal>> getFavMeals(String userId,NetworkCallBack networkCallBacks) {
        return FavDao.getFavoritesByUserId(userId);
    }

    @Override
    public void updateMeal(Meal meal) {

    }



}
