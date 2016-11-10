package com.example.webservice;

/**
 * Created by masrnec on 10.11.2016..
 */

public class WsDataRegistrationLoader extends DataLoader {

    @Override
    public void loadDataRegistration(DataLoadedListener dataLoadedListener,String first_name,String last_name,Integer phone, String email, Integer pass) {
        super.loadDataRegistration(dataLoadedListener,first_name,last_name,phone, email, pass);

        WebServiceCaller webServiceCaller = new WebServiceCaller(responseHandler);

        webServiceCaller.registrateUser(first_name,last_name,phone,email,pass);
    }

    WebServiceHandler responseHandler = new WebServiceHandler() {
        @Override
        public void onDataArrived(Object result, boolean ok) {
            if(ok){

                WebServiceResponseRegistration wsr = (WebServiceResponseRegistration) result;

                mDataLoadedListner.onDataLoaded(result);
            }
        }
    };
}
