package com.example.webservice;

/**
 * Created by Lovro on 3.12.2016..
 */

public class ReservationItemDetails {
    public String id;
    public String persons;
    public String meals;
    public String status;
    public String remark;
    public String description;
    public String data;
    public String time_arrival;
    public String time_checkout;
    public ReservationTableItemDetails[] tables;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public ReservationTableItemDetails[] getTables() {
        return tables;
    }

    public void setTables(ReservationTableItemDetails[] tables) {
        this.tables = tables;
    }
}
