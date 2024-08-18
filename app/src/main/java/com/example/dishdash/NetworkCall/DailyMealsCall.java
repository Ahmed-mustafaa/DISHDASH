package com.example.dishdash.NetworkCall;

import com.example.dishdash.model.Meal;

import java.util.List;

public interface DailyMealsCall {
   void OnSuccess(List<Meal> meals);


   void OnFailure(Throwable throwable);
}
