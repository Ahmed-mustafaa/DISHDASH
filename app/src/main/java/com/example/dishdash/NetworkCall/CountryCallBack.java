package com.example.dishdash.NetworkCall;

import com.example.dishdash.model.Country;

import java.util.List;

public interface CountryCallBack {
     void onSuccess(List<Country> countries);
     void onFailure(Throwable throwable);
}
