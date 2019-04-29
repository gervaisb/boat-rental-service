package com.github.gervaisb.boatrental.billing.domain;

import java.math.BigDecimal;
import java.time.Duration;

import com.github.gervaisb.boatrental.renting.domain.Customer;
import lombok.Value;

@Value
public class Bill {
    private final Customer customer;
    private final Duration duration;
    private final BigDecimal amount;
}
