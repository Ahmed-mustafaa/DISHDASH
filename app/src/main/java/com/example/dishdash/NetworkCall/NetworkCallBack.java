package com.example.dishdash.NetworkCall;

import com.example.dishdash.model.Meal;

import java.util.List;

public interface NetworkCallBack {
    void onSuccess(Meal meal);

    public void onFailure(Throwable t);
}
