package com.foi.webservice.data_loaders;

import com.foi.webservice.WebServiceCaller;
import com.foi.webservice.WebServiceHandler;
import com.foi.webservice.responses.WebServiceRequestForCancelDetails;

/**
 * Created by Matija on 21.1.2017..
 */

public class WsRequestForCancelDetails extends DataLoader {

    @Override
    public void loadRequestForCancelDetails(DataLoadedListener dataLoadedListener, String reservation) {
        super.loadRequestForCancelDetails(dataLoadedListener, reservation);

        WebServiceCaller webServiceCaller = new WebServiceCaller(responseHandler);
        webServiceCaller.getRequestForCancelDetails(reservation);
    }


    WebServiceHandler responseHandler = new WebServiceHandler() {
        @Override
        public void onDataArrived(Object result, boolean ok) {
            if(ok){
                WebServiceRequestForCancelDetails data = (WebServiceRequestForCancelDetails) result;
                mDataLoadedListner.onDataLoaded(result);
            }
        }
    };
}
