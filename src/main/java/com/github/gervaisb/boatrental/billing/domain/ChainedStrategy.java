package com.github.gervaisb.boatrental.billing.domain;

import java.math.BigDecimal;
import java.time.Duration;

public class ChainedStrategy implements BillingStrategy {

    private final BillingStrategy first;
    private final BillingStrategy second;

    public ChainedStrategy(BillingStrategy first, BillingStrategy second) {
        this.first = first;
        this.second = second;
    }

    public ChainedStrategy then(BillingStrategy another) {
        return new ChainedStrategy(this, another);
    }

    @Override
    public BigDecimal apply(BigDecimal price, Duration duration) {
        BigDecimal result = first.apply(price, duration);
        return second.apply(result, duration);
    }
}
