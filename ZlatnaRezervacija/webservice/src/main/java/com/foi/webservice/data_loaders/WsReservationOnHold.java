package com.foi.webservice.data_loaders;

import android.provider.ContactsContract;

import com.foi.webservice.WebServiceCaller;
import com.foi.webservice.WebServiceHandler;
import com.foi.webservice.responses.WebServiceResponseReservationOnHold;

/**
 * Created by Matija on 11.1.2017..
 */

public class WsReservationOnHold extends DataLoader {

    @Override
    public void loadReservationOnHold(DataLoadedListener dataLoadedListener, String reservation) {
        super.loadReservationOnHold(dataLoadedListener, reservation);

        WebServiceCaller webServiceCaller = new WebServiceCaller(responseHandler);

        webServiceCaller.getReservationOnHold(reservation);
    }

    WebServiceHandler responseHandler = new WebServiceHandler() {
        @Override
        public void onDataArrived(Object result, boolean ok) {
            if(ok){

                WebServiceResponseReservationOnHold wsr = (WebServiceResponseReservationOnHold) result;
                mDataLoadedListner.onDataLoaded(result);
            }
        }
    };
}
