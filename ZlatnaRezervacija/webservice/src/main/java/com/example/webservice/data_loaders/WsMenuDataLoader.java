package com.example.webservice.data_loaders;

import com.example.webservice.WebServiceCaller;
import com.example.webservice.WebServiceHandler;
import com.example.webservice.responses.WebServiceMenuResponse;

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
