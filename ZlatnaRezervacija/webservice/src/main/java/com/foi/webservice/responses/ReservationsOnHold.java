package com.foi.webservice.responses;

/**
 * Created by Matija on 10.1.2017..
 */
public class ReservationsOnHold {

    public String id;
    public String date;
    public String time_arrival;
    public String time_checkout;
    public String user_first_name;
    public String user_last_name;
    public String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime_arrival() {
        return time_arrival;
    }

    public void setTime_arrival(String time_arrival) {
        this.time_arrival = time_arrival;
    }

    public String getTime_checkout() {
        return time_checkout;
    }

    public void setTime_checkout(String time_checkout) {
        this.time_checkout = time_checkout;
    }

    public String getUser_first_name() {
        return user_first_name;
    }

    public void setUser_first_name(String user_first_name) { this.user_first_name = user_first_name; }

    public String getUser_last_name() {
        return user_last_name;
    }

    public void setUser_last_name(String user_last_name) {
        this.user_last_name = user_last_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) { this.status = status; }
}
