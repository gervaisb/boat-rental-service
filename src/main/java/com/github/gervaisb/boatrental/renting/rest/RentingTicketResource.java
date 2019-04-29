package com.github.gervaisb.boatrental.renting.rest;

import java.time.LocalDateTime;

import com.github.gervaisb.boatrental.renting.domain.RentingTicket;

import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


public class RentingTicketResource extends ResourceSupport {
    public LocalDateTime start;
    public String boatName;
    public String customerFirstName;
    public String customerLastName;
    public String reference;

    public RentingTicketResource(RentingTicket ticket) {
        start = ticket.getStart();
        boatName = ticket.getBoatName();
        customerLastName = ticket.getCustomer().getLastName();
        customerFirstName = ticket.getCustomer().getFirstName();
        reference = ticket.getReference().value;

        add(linkTo(methodOn(RentingController.class).arrival(reference))
                .withRel("return"));
    }
}
