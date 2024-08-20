package com.example.dishdash.NetworkCall;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    @GET("list.php?a=list")
    Call<CountryResponse> getCountries();
    /*@GET("list.php?c=list")
    Call<CountryResponse> getCategories();
*/
}
