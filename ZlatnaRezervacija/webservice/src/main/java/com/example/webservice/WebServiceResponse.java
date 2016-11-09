package com.example.webservice;

import java.lang.reflect.Array;

public class WebServiceResponse {
    public String status;
    public String name;
    public String email;
    public int user_id;
    public int role_id;


    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }


}
