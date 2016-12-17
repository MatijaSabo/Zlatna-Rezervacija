package com.foi.webservice.responses;

/**
 * Created by Lovro on 3.12.2016..
 */

public class WebServiceReservationResponse {

    public ReservationItemDetails[] reservations;

    public ReservationItemDetails[] getReservations() {
        return reservations;
    }

    public void setReservations(ReservationItemDetails[] reservations) {
        this.reservations = reservations;
    }
}
