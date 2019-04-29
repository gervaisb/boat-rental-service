package com.github.gervaisb.boatrental.billing.domain;

import java.math.BigDecimal;
import java.time.Duration;

public class WeatherStrategy implements BillingStrategy {
    @Override
    public BigDecimal apply(BigDecimal price, Duration duration) {
        return price;
    }
}
