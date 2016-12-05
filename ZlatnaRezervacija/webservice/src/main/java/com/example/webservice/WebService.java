package com.example.webservice;

import com.example.webservice.responses.WebServiceMenuResponse;
import com.example.webservice.responses.WebServiceReservationResponse;
import com.example.webservice.responses.WebServiceResponse;
import com.example.webservice.responses.WebServiceResponseRegistration;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;


public interface WebService {

    @FormUrlEncoded
    @POST("login.php")
    Call<WebServiceResponse> getUserData(@Field("email") String email, @Field("pass") int pass);

    @FormUrlEncoded
    @POST("registration.php")
    Call<WebServiceResponseRegistration> getStatusRegistration(@Field("first_name") String first_name, @Field("last_name")String last_name, @Field("phone")int phone, @Field("email") String email, @Field("pass") int password);

    @FormUrlEncoded
    @POST("menu.php")
    Call<WebServiceMenuResponse> getMenuData(@Field("category") String category);

    @FormUrlEncoded
    @POST("reservations_user.php")
    Call<WebServiceReservationResponse> getMyReservations(@Field("user") String user);

    @FormUrlEncoded
    @POST("create_reservation.php")
    Call<WebServiceResponseRegistration> createReservation(@Field("user") int user, @Field("persons") int persons, @Field("date") String date, @Field("time") String time, @Field("meals") int meals, @Field("remark") String remark);
}
