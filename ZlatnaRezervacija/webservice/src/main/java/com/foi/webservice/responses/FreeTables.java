package com.foi.webservice.responses;

/**
 * Created by Matija on 11.1.2017..
 */

public class FreeTables {
    public String id;
    public String label;
    public String number_of_seats;
    public String free_from;
    public String free_to;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public String getNumber_of_seats() { return number_of_seats; }
    public void setNumber_of_seats(String number_of_seats) { this.number_of_seats = number_of_seats; }

    public String getFree_from() { return free_from; }
    public void setFree_from(String free_from) { this.free_from = free_from; }

    public String getFree_to() { return free_to; }
    public void setFree_to(String free_to) { this.free_to = free_to; }
}
