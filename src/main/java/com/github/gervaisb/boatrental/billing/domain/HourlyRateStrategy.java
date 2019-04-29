package com.github.gervaisb.boatrental.billing.domain;

import java.math.BigDecimal;
import java.time.Duration;

public class HourlyRateStrategy implements BillingStrategy {
    private final BigDecimal rate;

    public HourlyRateStrategy(double rate) {
        this.rate = BigDecimal.valueOf(rate);
    }

    @Override
    public BigDecimal apply(BigDecimal price, Duration duration) {
        return price.add(price.multiply(rate));
    }
}
