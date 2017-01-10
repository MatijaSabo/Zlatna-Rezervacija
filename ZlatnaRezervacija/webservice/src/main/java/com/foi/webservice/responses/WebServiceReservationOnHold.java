package com.foi.webservice.responses;

/**
 * Created by Matija on 10.1.2017..
 */

public class WebServiceReservationOnHold {
    public ReservationsOnHold[] reservations;

    public ReservationsOnHold[] getReservations() {
        return reservations;
    }

    public void setReservations(ReservationsOnHold[] reservations) {
        this.reservations = reservations;

    }
}
