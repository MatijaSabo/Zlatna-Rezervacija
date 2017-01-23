package com.foi.webservice.responses;

/**
 * Created by Matija on 21.1.2017..
 */

public class WebServiceRequestForCancelDetails {
    public String id;
    public String date;
    public String persons;
    public String meals;
    public String time_arrival;
    public String time_checkout;
    public String remark;
    public String description;
    public String user_first_name;
    public String user_last_name;
    public String user_id;

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

    public String getPersons() {
        return persons;
    }
    public void setPersons(String persons) {
        this.persons = persons;
    }

    public String getMeals() {
        return meals;
    }
    public void setMeals(String meals) {
        this.meals = meals;
    }

    public String getTime_arrival() {
        return time_arrival;
    }
    public void setTime_arrival(String time_arrival) {
        this.time_arrival = time_arrival;
    }

    public String getTime_checkout() {
        if(time_checkout == null){
            time_checkout = "Nije definirano";
        }
        return time_checkout;
    }
    public void setTime_checkout(String time_checkout) {
        this.time_checkout = time_checkout;
    }

    public String getRemark() {
        if(remark == null){
            remark = "Nema opisa";
        }
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser_first_name() {
        return user_first_name;
    }
    public void setUser_first_name(String user_first_name) {
        this.user_first_name = user_first_name;
    }

    public String getUser_last_name() {
        return user_last_name;
    }
    public void setUser_last_name(String user_last_name) {
        this.user_last_name = user_last_name;
    }

    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.id = user_id;
    }
}
