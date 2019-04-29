package com.github.gervaisb.boatrental.billing.domain;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import com.github.gervaisb.boatrental.renting.domain.Customer;
import com.github.gervaisb.boatrental.renting.domain.Rent;
import com.github.gervaisb.boatrental.renting.domain.RentId;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class BillingServiceTest {

    private final Customer noah = new Customer("Noah", "Salvator");
    private final Rent rent = new Rent(new RentId(), noah, LocalDateTime.now());
    private final Duration threeHours = Duration.ofHours(3);
    private final BillingStrategy strategy = mock(BillingStrategy.class);
    private final BillingService subject = new BillingService(strategy);

    @Test
    public void compute_must_produce_a_Bill_from_parameters() {
        given(strategy.apply(any(), any())).willReturn(BigDecimal.valueOf(42));
        Bill aBill = subject.compute(rent, threeHours);

        Assertions.assertThat(aBill)
                .hasFieldOrPropertyWithValue("Customer.LastName", noah.getLastName())
                .hasFieldOrPropertyWithValue("Customer.FirstName", noah.getFirstName())
                .hasFieldOrPropertyWithValue("Duration", threeHours)
                .hasFieldOrProperty("Amount");
    }
}
