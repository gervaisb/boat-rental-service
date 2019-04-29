package com.github.gervaisb.boatrental.renting.rest;

import com.github.gervaisb.boatrental.renting.domain.BoatName;
import com.github.gervaisb.boatrental.renting.domain.Customer;
import com.github.gervaisb.boatrental.renting.domain.RentId;
import com.github.gervaisb.boatrental.renting.domain.RentingService;
import com.github.gervaisb.boatrental.renting.domain.RentingTicket;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/api/rents")
public class RentingController {

    private final RentingService service;

    public RentingController(RentingService service) {
        this.service = service;
    }

    @PostMapping
    public RentingTicketResource rent(RentCommand command) {
        RentingTicket ticket = service.rent(
                new BoatName(command.boat),
                new Customer(command.customerFirstName, command.customerLastName));
        return new RentingTicketResource(ticket);
    }

    @DeleteMapping("/{reference}")
    public RentingTicketResource arrival(@PathVariable String reference) {
        return new RentingTicketResource(service.get(new RentId(reference)));
    }

}
