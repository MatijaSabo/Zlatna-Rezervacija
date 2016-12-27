package com.foi.webservice.data_loaders;

import com.foi.webservice.WebServiceCaller;
import com.foi.webservice.WebServiceHandler;
import com.foi.webservice.responses.WebServiceReservationCancelResponse;

/**
 * Created by Lovro on 27.12.2016..
 */

public class WsReservationCancelDataLoader extends DataLoader{

    @Override
    public void loadReservationCancel(DataLoadedListener dataLoadedListener, int reservation, String description) {
        super.loadReservationCancel(dataLoadedListener, reservation, description);

        WebServiceCaller webServiceCaller = new WebServiceCaller(responseHandler);

        webServiceCaller.sendReservationCancelRequest(reservation, description);
    }

    WebServiceHandler responseHandler = new WebServiceHandler() {
        @Override
        public void onDataArrived(Object result, boolean ok) {
            if(ok){

                WebServiceReservationCancelResponse wsr = (WebServiceReservationCancelResponse) result;
                mDataLoadedListner.onDataLoaded(result);
            }
        }
    };

}
