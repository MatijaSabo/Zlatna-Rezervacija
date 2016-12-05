package com.example.webservice.responses;

/**
 * Created by Matija on 22.11.2016..
 */

public class MenuItemDetails {
    public String name;
    public String description;
    public String price;

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getPrice() { return price; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(String price) { this.price = price; }
}
