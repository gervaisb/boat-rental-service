package com.github.gervaisb.boatrental.billing.domain;

import java.math.BigDecimal;
import java.time.Duration;

import com.github.gervaisb.boatrental.renting.domain.Rent;

import org.springframework.stereotype.Service;

@Service
public class BillingService {
    private final BillingStrategy strategy;

    public BillingService(BillingStrategy strategy) {
        this.strategy = strategy;
    }

    public Bill compute(Rent rent, Duration duration) {
        BigDecimal price = strategy.apply(BigDecimal.ZERO, duration);
        return new Bill(rent.getCustomer(), duration, price);
    }
}
