package com.foi.webservice.responses;

/**
 * Created by masrnec on 27.1.2017..
 */

public class WebServiceResponseNotification {
    public String success;
    public String failure;

    public String getSuccess() {
        return success;
    }
    public void setSuccess(String success) {
        this.success = success;
    }

    public String getFailure() { return failure; }
    public void setFailure(String failure) { this.failure = failure; }
}
