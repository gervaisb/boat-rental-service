package com.github.gervaisb.boatrental.renting.domain;

import java.time.Clock;
import java.time.LocalDateTime;

import com.github.gervaisb.boatrental.billing.domain.Bill;
import com.github.gervaisb.boatrental.billing.domain.BillingService;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class RentingService {

    private final ApplicationEventPublisher events;
    private final BillingService billing;
    private final RentsRepository rents;
    private final Clock clock;

    public RentingService(ApplicationEventPublisher events, Clock clock, RentsRepository rents, BillingService billing) {
        this.events = events;
        this.billing = billing;
        this.rents = rents;
        this.clock = clock;
    }

    public RentingTicket rent(BoatName boat, Customer customer) {
        Rent rent = new Rent(new RentId(), customer, now());
        rents.save(rent);
        return new RentingTicket(rent, boat);
    }

    public Bill arrival(RentingTicket ticket) {
        Rent rent = rents.getById(ticket.getReference());
        Bill bill = rent.finish(clock, billing);
        rents.save(rent);
        events.publishEvent(new RentFinishedEvent(this, rent, bill.getDuration()));
        return bill;
    }

    private LocalDateTime now() {
        return LocalDateTime.ofInstant(clock.instant(), clock.getZone());
    }

    public RentingTicket get(RentId rentId) {
        Rent rent = rents.getById(rentId);
        return new RentingTicket(rent, null);
    }
}
