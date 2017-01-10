package com.foi.webservice.data_loaders;

import com.foi.webservice.WebServiceCaller;
import com.foi.webservice.WebServiceHandler;
import com.foi.webservice.responses.WebServiceReservationOnHold;
import com.foi.webservice.responses.WebServiceReservationResponse;

/**
 * Created by Matija on 10.1.2017..
 */

public class WsReservationsOnHold extends DataLoader {

    @Override
    public void loadReservationsOnHold(DataLoadedListener dataLoadedListener, String restaurant) {
        super.loadReservationsOnHold(dataLoadedListener, restaurant);

        WebServiceCaller webServiceCaller = new WebServiceCaller(responseHandler);

        webServiceCaller.getReservationsOnHold(restaurant);
    }

    WebServiceHandler responseHandler = new WebServiceHandler() {
        @Override
        public void onDataArrived(Object result, boolean ok) {

            if(ok){
                WebServiceReservationOnHold wsr = (WebServiceReservationOnHold) result;
                mDataLoadedListner.onDataLoaded(result);
            }
        }
    };

}
