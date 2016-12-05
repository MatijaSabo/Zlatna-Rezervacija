package com.example.webservice.responses;

import java.lang.reflect.Array;

public class WebServiceResponse {
    public String status;
    public String name;
    public String email;
    public String user_id;
    public String user_role;


    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getRole_id() {
        return user_role;
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

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setRole_id(String user_role) {
        this.user_role = user_role;
    }


}
