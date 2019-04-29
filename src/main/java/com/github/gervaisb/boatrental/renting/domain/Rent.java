package com.github.gervaisb.boatrental.renting.domain;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;

import com.github.gervaisb.boatrental.billing.domain.Bill;
import com.github.gervaisb.boatrental.billing.domain.BillingService;

public class Rent {
    public final RentId id;
    private final Customer customer;
    private final LocalDateTime start;

    public Rent(RentId id, Customer customer, LocalDateTime start) {
        this.customer = customer;
        this.start = start;
        this.id = id;
    }

    public Bill finish(Clock clock, BillingService billing) {
        LocalDateTime end = LocalDateTime.ofInstant(clock.instant(), clock.getZone());
        Duration duration = Duration.between(start, end);
        return billing.compute(this, duration);
    }

    public Customer getCustomer() {
        return customer;
    }

    public LocalDateTime getStart() {
        return start;
    }
}
