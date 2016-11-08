package com.example.webservice;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;


public interface WebService {

    @FormUrlEncoded
    @POST("login.php")
    Call<WebService> getUserData(@Field("method") String method);

    @FormUrlEncoded
    @POST("registration.php")
    Call<WebService> saveUser(@Field("method") String method);


}
