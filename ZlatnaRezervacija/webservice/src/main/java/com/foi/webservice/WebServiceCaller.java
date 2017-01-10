package com.foi.webservice;

import android.support.v7.app.AppCompatActivity;

import com.foi.webservice.responses.WebServiceMenuResponse;
import com.foi.webservice.responses.WebServiceReservationCancelResponse;
import com.foi.webservice.responses.WebServiceReservationOnHold;
import com.foi.webservice.responses.WebServiceReservationResponse;
import com.foi.webservice.responses.WebServiceResponse;
import com.foi.webservice.responses.WebServiceResponseRegistration;
import com.foi.webservice.responses.WebServiceResponseSettings;
import com.squareup.okhttp.OkHttpClient;

import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class WebServiceCaller extends AppCompatActivity {

    Retrofit retrofit;
    WebServiceHandler mWebServiceHandler;

    private final String baseUrl = "https://barka.foi.hr/WebDiP/2015_projekti/WebDiP2015x070/zlatna_rezervacija/";

    public WebServiceCaller(WebServiceHandler webServiceHandler) {
        this.mWebServiceHandler = webServiceHandler;
        OkHttpClient client = new OkHttpClient();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public void registrateUser(final String first_name,final String last_name, final Integer phone, final String email, final Integer pass ) {
        WebService serviceCaller = retrofit.create(WebService.class);
        retrofit.Call<WebServiceResponseRegistration> call = serviceCaller.getStatusRegistration(first_name,last_name,phone,email,pass);
            call.enqueue(new Callback<WebServiceResponseRegistration>() {
                @Override
                public void onResponse(Response<WebServiceResponseRegistration> response, Retrofit retrofit) {
                    try {
                        if (response.isSuccess() ) {
                            handleResponseRegistration((WebServiceResponseRegistration) response.body());
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();

                    }
                }

                @Override
                public void onFailure(Throwable t) {

                    
                }
            });

    }
    public void changeSettings(final String user,final String type ) {
        WebService serviceCaller = retrofit.create(WebService.class);
        retrofit.Call<WebServiceResponseSettings> call = serviceCaller.getStatusSettings(user,type);
        call.enqueue(new Callback<WebServiceResponseSettings>() {
            @Override
            public void onResponse(Response<WebServiceResponseSettings> response, Retrofit retrofit) {
                try {
                    if (response.isSuccess() ) {
                        handleResponseSettings((WebServiceResponseSettings) response.body());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();

                }
            }

            @Override
            public void onFailure(Throwable t) {


            }
        });

    }
    public void getAll(final String method, final int pass){
        WebService  webServiceRezervacije=retrofit.create(WebService.class);
        retrofit.Call<WebServiceResponse> call=webServiceRezervacije.getUserData(method, pass);
        call.enqueue(new Callback<WebServiceResponse>() {
            @Override
            public void onResponse(Response<WebServiceResponse> response, Retrofit retrofit) {
                try {
                    if(response.isSuccess()){
                        handleResponse((WebServiceResponse) response.body());
                    }
                }catch (Exception ex){
                    System.out.println(ex);
                }
            }
            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void getMenuItems(final String category){
        WebService serviceCaller=retrofit.create(WebService.class);
        retrofit.Call<WebServiceMenuResponse> call=serviceCaller.getMenuData(category);
        call.enqueue(new Callback<WebServiceMenuResponse>() {
            @Override
            public void onResponse(Response<WebServiceMenuResponse> response, Retrofit retrofit) {
                try {
                    if(response.isSuccess()){
                        handleMenuDataResponse((WebServiceMenuResponse) response.body());
                    }
                }catch (Exception ex){
                    System.out.println(ex);
                }
            }
            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void getMyAllReservation(final String user){
        WebService serviceCaller=retrofit.create(WebService.class);
        retrofit.Call<WebServiceReservationResponse> call=serviceCaller.getMyReservations(user);
        call.enqueue(new Callback<WebServiceReservationResponse>() {
            @Override
            public void onResponse(Response<WebServiceReservationResponse> response, Retrofit retrofit) {
                try{
                    if(response.isSuccess()){
                        handleMyReservationsResponse((WebServiceReservationResponse) response.body());
                    }

                }catch (Exception ex){
                    System.out.println(ex);
                }
            }
            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void sendReservationRequest(final int user, final int persons, final String date, final String time, final int meals, final String remark){
        WebService serviceCaller=retrofit.create(WebService.class);
        retrofit.Call<WebServiceResponseRegistration> call=serviceCaller.createReservation(user, persons, date, time, meals, remark);
        call.enqueue(new Callback<WebServiceResponseRegistration>() {
            @Override
            public void onResponse(Response<WebServiceResponseRegistration> response, Retrofit retrofit) {
                try {
                    if(response.isSuccess()){
                        System.out.println(response.body().getStatus());
                        handleCreateReservationResponse((WebServiceResponseRegistration) response.body());
                    }
                }catch (Exception ex){
                    System.out.println(ex);
                }
            }
            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void sendReservationCancelRequest(final int status, final String description) {

            WebService serviceCaller = retrofit.create(WebService.class);
            retrofit.Call<WebServiceReservationCancelResponse> call = serviceCaller.getReservationCancel(status, description);
            call.enqueue(new Callback<WebServiceReservationCancelResponse>() {
                @Override
                public void onResponse(Response<WebServiceReservationCancelResponse> response, Retrofit retrofit) {
                    try {
                        if (response.isSuccess()) {
                            handleMyReservationsCancelResponse((WebServiceReservationCancelResponse) response.body());
                        }

                    } catch (Exception ex) {
                        System.out.println(ex);
                    }

                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
    }

    public void getReservationsOnHold(final String restaurant) {

        WebService serviceCaller = retrofit.create(WebService.class);
        retrofit.Call<WebServiceReservationOnHold> call = serviceCaller.getReservationOnHold(restaurant);
        call.enqueue(new Callback<WebServiceReservationOnHold>() {
            @Override
            public void onResponse(Response<WebServiceReservationOnHold> response, Retrofit retrofit) {
                try {
                    if (response.isSuccess()) {
                        System.out.println("1");
                        handleReservationsOnHold((WebServiceReservationOnHold) response.body());
                    }

                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


    private void handleCreateReservationResponse(WebServiceResponseRegistration response) {
        if(mWebServiceHandler != null){
            mWebServiceHandler.onDataArrived(response, true);
        }
    }

    private void handleResponse(WebServiceResponse response) {
        if(mWebServiceHandler!= null){
            mWebServiceHandler.onDataArrived(response, true);
        }
    }
    private void handleResponseRegistration(WebServiceResponseRegistration response){
        if(mWebServiceHandler !=null){
            mWebServiceHandler.onDataArrived(response,true);
        }
    }
    private void handleResponseSettings(WebServiceResponseSettings response){
        if(mWebServiceHandler !=null){
            mWebServiceHandler.onDataArrived(response,true);
        }
    }

    private void handleMenuDataResponse(WebServiceMenuResponse response){

        if(mWebServiceHandler != null) {
            mWebServiceHandler.onDataArrived(response, true);
        }
    }

    private void handleMyReservationsResponse(WebServiceReservationResponse response){

        if(mWebServiceHandler != null) {
            mWebServiceHandler.onDataArrived(response, true);
        }
    }

    private void handleMyReservationsCancelResponse(WebServiceReservationCancelResponse response) {

        if (mWebServiceHandler != null) {
            mWebServiceHandler.onDataArrived(response, true);
        }
    }

    private void handleReservationsOnHold(WebServiceReservationOnHold response) {

        if (mWebServiceHandler != null) {
            System.out.println("2");
            mWebServiceHandler.onDataArrived(response, true);
        }
    }
}