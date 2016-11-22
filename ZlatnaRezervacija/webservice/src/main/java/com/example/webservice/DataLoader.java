package com.example.webservice;

/**
 * Created by Matija on 10.11.2016..
 */

public abstract class DataLoader {
    public Object result;

    protected DataLoadedListener mDataLoadedListner;

    public void loadData(DataLoadedListener dataLoadedListener, String email, Integer pass){
        this.mDataLoadedListner = dataLoadedListener;
    }

    public void loadDataRegistration(DataLoadedListener dataLoadedListener,String first_name,String last_name,  Integer phone,String email, Integer pass ){
        this.mDataLoadedListner = dataLoadedListener;
    }

    public void loadMenuData(DataLoadedListener dataLoadedListener, String category){
        this.mDataLoadedListner = dataLoadedListener;
    }

    public boolean dataLoaded(){
        if(result == null){
            return false;
        } else {
            return true;
        }
    }

}
