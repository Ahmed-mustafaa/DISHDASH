package com.example.dishdash.NetworkCall;

import com.example.dishdash.model.Country;
import com.example.dishdash.model.Meal;

import java.util.List;

public interface CountryCallBack {
     void onSuccess(List<Country> countries);
     void onCountryMealsSuccess(List<Meal> meals );
     void onFailure(Throwable throwable);
}
