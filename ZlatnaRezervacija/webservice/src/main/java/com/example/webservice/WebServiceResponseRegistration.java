package com.example.webservice;

import com.google.gson.annotations.SerializedName;

/**
 * Created by masrnec on 9.11.2016..
 */

public class WebServiceResponseRegistration {
  //  @SerializedName("status")
    public String status;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
