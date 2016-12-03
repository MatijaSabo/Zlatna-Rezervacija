package com.example.webservice;

/**
 * Created by Lovro on 3.12.2016..
 */

public class WsReservationsDataLoader extends DataLoader {

    @Override
    public void loadDataMyReservations(DataLoadedListener dataLoadedListener, String user) {
        super.loadDataMyReservations(dataLoadedListener, user);

        WebServiceCaller webServiceCaller = new WebServiceCaller(responseHandler);

        webServiceCaller.getMyAllReservation(user);
    }

    WebServiceHandler responseHandler = new WebServiceHandler() {
        @Override
        public void onDataArrived(Object result, boolean ok) {
            if(ok){

                WebServiceReservationResponse wsr = (WebServiceReservationResponse) result;
                mDataLoadedListner.onDataLoaded(result);
            }
        }
    };

}
