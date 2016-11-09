package com.example.webservice;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import com.squareup.okhttp.OkHttpClient;

import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class WebServiceCaller extends AppCompatActivity {

    Retrofit retrofit;

    private final String baseUrl = "https://barka.foi.hr/WebDiP/2015_projekti/WebDiP2015x070/zlatna_rezervacija/";
    //ButterKnife.bind(this);

    // constructor
    public WebServiceCaller() {

        OkHttpClient client = new OkHttpClient();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public void registrateUser(String first_name,String last_name,String email,int phone,int pass,int role_id) {
        WebService serviceCaller = retrofit.create(WebService.class);
        Call<WebServiceResponseRegistration> call = serviceCaller.getStatusRegistration(first_name,last_name,phone,email,pass,role_id);


        if (call != null) {
            call.enqueue(new Callback<WebServiceResponseRegistration>() {
                @Override
                public void onResponse(Response<WebServiceResponseRegistration> response, Retrofit retrofit) {
                    String a=response.body().getStatus();
                    try {
                        if (response.equals("1") ) {

                        } else {

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
    }
}