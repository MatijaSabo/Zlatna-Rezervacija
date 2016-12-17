package com.foi.webservice.data_loaders;

import com.foi.webservice.WebServiceCaller;
import com.foi.webservice.WebServiceHandler;
import com.foi.webservice.responses.WebServiceMenuResponse;

/**
 * Created by Matija on 22.11.2016..
 */

public class WsMenuDataLoader extends DataLoader {
    @Override
    public void loadMenuData(DataLoadedListener dataLoadedListener, String category) {
        super.loadMenuData(dataLoadedListener, category);

        WebServiceCaller webServiceCaller = new WebServiceCaller(responseHandler);
        webServiceCaller.getMenuItems(category);
    }


    WebServiceHandler responseHandler = new WebServiceHandler() {
        @Override
        public void onDataArrived(Object result, boolean ok) {
            if(ok){
                WebServiceMenuResponse proba = (WebServiceMenuResponse) result;
                mDataLoadedListner.onDataLoaded(result);
            }
        }
    };
}
