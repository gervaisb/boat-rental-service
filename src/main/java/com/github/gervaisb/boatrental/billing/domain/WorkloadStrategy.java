package com.github.gervaisb.boatrental.billing.domain;

import java.math.BigDecimal;
import java.time.Duration;

public class WorkloadStrategy implements BillingStrategy {
    @Override
    public BigDecimal apply(BigDecimal price, Duration duration) {
        return price;
    }
}
