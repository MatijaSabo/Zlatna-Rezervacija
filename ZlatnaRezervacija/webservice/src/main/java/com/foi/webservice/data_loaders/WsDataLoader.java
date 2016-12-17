package com.foi.webservice.data_loaders;

import com.foi.webservice.WebServiceCaller;
import com.foi.webservice.WebServiceHandler;
import com.foi.webservice.responses.WebServiceResponse;

/**
 * Created by Matija on 10.11.2016..
 */

public class WsDataLoader extends DataLoader {

    @Override
    public void loadData(DataLoadedListener dataLoadedListener, String email, Integer pass) {
        super.loadData(dataLoadedListener, email, pass);

        WebServiceCaller webServiceCaller = new WebServiceCaller(responseHandler);

        webServiceCaller.getAll(email, pass);
    }

    WebServiceHandler responseHandler = new WebServiceHandler() {
        @Override
        public void onDataArrived(Object result, boolean ok) {
            if(ok){

                WebServiceResponse proba = (WebServiceResponse) result;

                mDataLoadedListner.onDataLoaded(result);
            }
        }
    };
}
