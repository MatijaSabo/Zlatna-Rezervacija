package com.foi.webservice.data_loaders;

import com.foi.webservice.WebServiceCaller;
import com.foi.webservice.WebServiceHandler;
import com.foi.webservice.responses.WebServiceResponseNotification;

/**
 * Created by masrnec on 27.1.2017..
 */

public class WsNotificationLoader extends DataLoader {

    @Override
    public void loadNotification(DataLoadedListener dataLoadedListener, int user, String message) {
        super.loadNotification(dataLoadedListener,user,message);
        WebServiceCaller webServiceCaller = new WebServiceCaller(responseHandler);

        webServiceCaller.sendNotification(user,message);
    }

    WebServiceHandler responseHandler = new WebServiceHandler() {
        @Override
        public void onDataArrived(Object result, boolean ok) {
            if(ok){

                WebServiceResponseNotification wsr = (WebServiceResponseNotification) result;

                mDataLoadedListner.onDataLoaded(result);
            }
        }
    };

    }

