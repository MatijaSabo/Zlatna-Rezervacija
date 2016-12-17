package com.foi.webservice.responses;

/**
 * Created by Matija on 22.11.2016..
 */
public class WebServiceMenuResponse {
    public MenuItemDetails[] items;

    public MenuItemDetails[] getItems() { return items; }

    public void setItems(MenuItemDetails[] items)
    {
        this.items = items;
    }
}
