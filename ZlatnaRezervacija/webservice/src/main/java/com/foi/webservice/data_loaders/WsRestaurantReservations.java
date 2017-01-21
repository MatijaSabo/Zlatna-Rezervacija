package com.foi.webservice.data_loaders;

import com.foi.webservice.WebServiceCaller;
import com.foi.webservice.WebServiceHandler;
import com.foi.webservice.responses.WebServiceReservationResponse;

/**
 * Created by Lovro on 21.1.2017..
 */

public class WsRestaurantReservations  extends DataLoader{

    @Override
    public void loadRestaurantReservations(DataLoadedListener dataLoadedListener, String restaurant) {
        super.loadRestaurantReservations(dataLoadedListener, restaurant);

        WebServiceCaller webServiceCaller = new WebServiceCaller(responseHandler);

        webServiceCaller.getRestaurantReservations(restaurant);
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
