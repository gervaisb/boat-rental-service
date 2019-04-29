package com.github.gervaisb.boatrental.renting.domain;

import java.time.LocalDateTime;

import lombok.Value;

@Value
public class RentingTicket {
    private final LocalDateTime start;
    private final String boatName;
    private final Customer customer;
    private final RentId reference;

    public RentingTicket(Rent rent, BoatName boat) {
        this.customer = rent.getCustomer();
        this.boatName = boat.getName();
        this.start = rent.getStart();
        this.reference = rent.id;
    }



}
