package com.example.webservice.data_loaders;

import com.example.webservice.WebServiceCaller;
import com.example.webservice.WebServiceHandler;
import com.example.webservice.responses.WebServiceResponseRegistration;

/**
 * Created by Matija on 3.12.2016..
 */

public class WsCreateReservationDataLoader extends DataLoader {
    @Override
    public void loadReservationCreateStatus(DataLoadedListener dataLoadedListener, int user, int persons, String date, String time, int meals, String remark) {
        super.loadReservationCreateStatus(dataLoadedListener, user, persons, date, time, meals, remark);

        WebServiceCaller webServiceCaller = new WebServiceCaller(responseHandler);

        webServiceCaller.sendReservationRequest(user, persons, date, time, meals, remark);
    }

    WebServiceHandler responseHandler = new WebServiceHandler() {
        @Override
        public void onDataArrived(Object result, boolean ok) {
            if(ok){

                WebServiceResponseRegistration wsr = (WebServiceResponseRegistration) result;
                mDataLoadedListner.onDataLoaded(result);
            }
        }
    };
}
