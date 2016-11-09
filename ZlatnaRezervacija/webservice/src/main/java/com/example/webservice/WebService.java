package com.example.webservice;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;


public interface WebService {

    @FormUrlEncoded
    @POST("login.php")
    Call<WebService> getUserData(@Field("method") String method);

    @FormUrlEncoded
    @POST("registration.php")
    Call<WebServiceResponseRegistration> getStatusRegistration(@Field("first_name") String first_name,@Field("last_name")String last_name,@Field("phone")int phone,@Field("email") String email,@Field("pass") int password,@Field("role_id") int role_id);


}
