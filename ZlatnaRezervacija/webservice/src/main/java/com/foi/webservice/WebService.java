package com.foi.webservice;

import com.foi.webservice.responses.WebServiceMenuResponse;
import com.foi.webservice.responses.WebServiceRequestForCancelDetails;
import com.foi.webservice.responses.WebServiceReservationCancelResponse;
import com.foi.webservice.responses.WebServiceReservationOnHold;
import com.foi.webservice.responses.WebServiceReservationResponse;
import com.foi.webservice.responses.WebServiceResponse;
import com.foi.webservice.responses.WebServiceResponseRegistration;
import com.foi.webservice.responses.WebServiceResponseReservationOnHold;
import com.foi.webservice.responses.WebServiceResponseSettings;

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

    @FormUrlEncoded
    @POST("change_notifications.php")
    Call<WebServiceResponseSettings> getStatusSettings(@Field("user") String user, @Field("type") String type);

    @FormUrlEncoded
    @POST("cancel_reservation.php")
    Call<WebServiceReservationCancelResponse> getReservationCancel(@Field("reservation") int reservation, @Field("description") String description);

    @FormUrlEncoded
    @POST("reservations_on_hold.php")
    Call<WebServiceReservationOnHold> getReservationsOnHold(@Field("restaurant") String reservation);

    @FormUrlEncoded
    @POST("reservation_on_hold.php")
    Call<WebServiceResponseReservationOnHold> getReservationOnHold(@Field("reservation") String reservation);

    @FormUrlEncoded
    @POST("reservations_restaurant.php")
    Call<WebServiceReservationResponse> getRestaurantReservations(@Field("restaurant") String restaurant);

    @FormUrlEncoded
    @POST("request_for_cancel_details.php")
    Call<WebServiceRequestForCancelDetails> getRequestForCancelData(@Field("reservation") String reservation);

    @FormUrlEncoded
    @POST("request_for_cancel_reply.php")
    Call<WebServiceReservationCancelResponse> getReservationCancelResponse(@Field("reservation") String reservation, @Field("status") String status, @Field("description") String description);
}
