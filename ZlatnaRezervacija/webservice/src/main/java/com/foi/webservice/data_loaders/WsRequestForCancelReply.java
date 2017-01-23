package com.foi.webservice.data_loaders;

import com.foi.webservice.WebServiceCaller;
import com.foi.webservice.WebServiceHandler;
import com.foi.webservice.responses.WebServiceRequestForCancelDetails;
import com.foi.webservice.responses.WebServiceReservationCancelResponse;

/**
 * Created by Matija on 23.1.2017..
 */

public class WsRequestForCancelReply extends DataLoader {

    @Override
    public void loadReservationCancelResponse(DataLoadedListener dataLoadedListener, String reservation, String status, String description) {
        super.loadReservationCancelResponse(dataLoadedListener, reservation, status, description);

        WebServiceCaller webServiceCaller = new WebServiceCaller(responseHandler);
        webServiceCaller.getReservationCancelResponse(reservation, status, description);
    }


    WebServiceHandler responseHandler = new WebServiceHandler() {
        @Override
        public void onDataArrived(Object result, boolean ok) {
            if(ok){
                WebServiceReservationCancelResponse data = (WebServiceReservationCancelResponse) result;
                mDataLoadedListner.onDataLoaded(result);
            }
        }
    };

}
