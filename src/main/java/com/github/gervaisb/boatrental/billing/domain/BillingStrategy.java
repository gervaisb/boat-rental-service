package com.github.gervaisb.boatrental.billing.domain;

import java.math.BigDecimal;
import java.time.Duration;

public interface BillingStrategy {
    BigDecimal apply(BigDecimal price, Duration duration);
}
