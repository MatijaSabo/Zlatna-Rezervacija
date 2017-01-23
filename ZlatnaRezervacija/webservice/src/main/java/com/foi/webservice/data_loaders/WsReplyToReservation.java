package com.foi.webservice.data_loaders;

import com.foi.webservice.WebServiceCaller;
import com.foi.webservice.WebServiceHandler;
import com.foi.webservice.responses.WebServiceResponseSettings;

/**
 * Created by Matija on 23.1.2017..
 */

public class WsReplyToReservation extends DataLoader {

    @Override
    public void loadReplyToResrvationResponse(DataLoadedListener dataLoadedListener, String reservation, String status, String time, String tables, String description) {
        super.loadReplyToResrvationResponse(dataLoadedListener, reservation, status, time, tables, description);

        WebServiceCaller webServiceCaller = new WebServiceCaller(responseHandler);
        webServiceCaller.getReplyToResrvationResponse(reservation, status, time, tables, description);
    }


    WebServiceHandler responseHandler = new WebServiceHandler() {
        @Override
        public void onDataArrived(Object result, boolean ok) {
            if(ok){
                WebServiceResponseSettings data = (WebServiceResponseSettings) result;
                mDataLoadedListner.onDataLoaded(result);
            }
        }
    };

}
