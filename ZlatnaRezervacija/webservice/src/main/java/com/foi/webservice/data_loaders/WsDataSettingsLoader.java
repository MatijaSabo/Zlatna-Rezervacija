package com.foi.webservice.data_loaders;

import com.foi.webservice.WebServiceCaller;
import com.foi.webservice.WebServiceHandler;
import com.foi.webservice.responses.WebServiceResponseRegistration;
import com.foi.webservice.responses.WebServiceResponseSettings;

/**
 * Created by masrnec on 21.12.2016..
 */

public class WsDataSettingsLoader extends DataLoader {

    @Override
    public void loadDataSettings(DataLoadedListener dataLoadedListener, String user, String type) {
        super.loadDataSettings(dataLoadedListener,user,type);

        WebServiceCaller webServiceCaller = new WebServiceCaller(responseHandler);

        webServiceCaller.changeSettings(user,type);
    }
    WebServiceHandler responseHandler = new WebServiceHandler() {
        @Override
        public void onDataArrived(Object result, boolean ok) {
            if(ok){

                WebServiceResponseSettings wsr = (WebServiceResponseSettings) result;

                mDataLoadedListner.onDataLoaded(result);
            }
        }
    };
}
