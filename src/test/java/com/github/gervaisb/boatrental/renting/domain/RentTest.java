package com.github.gervaisb.boatrental.renting.domain;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import com.github.gervaisb.boatrental.billing.domain.Bill;
import com.github.gervaisb.boatrental.billing.domain.BillingService;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RentTest {

    private final Customer noah = new Customer("Noah", "Salvator");
    private final LocalDateTime start = LocalDateTime.now();
    private final Clock clock = mock(Clock.class);
    private final BillingService billing = mock(BillingService.class);
    private final Bill aBill = new Bill(noah, Duration.ofHours(3), BigDecimal.valueOf(42));

    private final Rent subject = new Rent(new RentId(), noah, start);

    @Before
    public void setup() {
        given(clock.getZone()).willReturn(ZoneId.systemDefault());
    }

    @Test
    public void finish_must_produce_the_Bill() {
        given(clock.instant()).willReturn(threeHoursLater());
        given(billing.compute(any(), any())).willReturn(aBill);

        Bill theBill = subject.finish(clock, billing);

        verify(billing).compute(any(), any());
        assertThat(theBill).isSameAs(aBill);
    }

    private Instant threeHoursLater() {
        ZoneOffset offset = ZoneId.systemDefault().getRules().getOffset(start);
        return start.toInstant(offset).plus(3, ChronoUnit.HOURS);
    }
}
