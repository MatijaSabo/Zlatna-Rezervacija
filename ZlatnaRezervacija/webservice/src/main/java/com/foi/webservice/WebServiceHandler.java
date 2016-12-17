package com.foi.webservice;

/**
 * Created by Matija on 10.11.2016..
 */
public interface WebServiceHandler {
    void onDataArrived(Object result, boolean ok);
}
