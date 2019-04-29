package com.github.gervaisb.boatrental.renting.domain;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.github.gervaisb.boatrental.billing.domain.Bill;
import com.github.gervaisb.boatrental.billing.domain.BillingService;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RentingServiceTest {

    private final Instant now = Instant.now();
    private final Clock clock = Clock.fixed(now, ZoneId.of("Zulu"));
    private final ApplicationEventPublisher events = mock(ApplicationEventPublisher.class);
    private final RentsRepository rents = mock(RentsRepository.class);
    private final BillingService billing = mock(BillingService.class);
    private final Customer noah = new Customer("Noah", "Salvator");
    private final BoatName ark = new BoatName("Ark");

    private final RentingService subject = new RentingService(events, clock, rents, billing);


    @Test
    public void rent_must_produce_a_RentingTicket() {
        RentingTicket ticket = subject.rent(ark, noah);

        assertThat(ticket)
                .hasFieldOrPropertyWithValue("Customer.LastName", noah.getLastName())
                .hasFieldOrPropertyWithValue("Customer.FirstName", noah.getFirstName())
                .hasFieldOrPropertyWithValue("Start", currentDateTime())
                .hasFieldOrPropertyWithValue("BoatName", "Ark");
    }

    @Test
    public void arrival_must_produce_a_Bill() {
        Rent aRent = new Rent(new RentId(), noah, currentDateTime().minusHours(3));
        Bill aBill = new Bill(noah, Duration.ofHours(3), BigDecimal.valueOf(42));
        given(billing.compute(eq(aRent), any())).willReturn(aBill);
        given(rents.getById(aRent.id)).willReturn(aRent);

        Bill theBill = subject.arrival(new RentingTicket(aRent, ark));

        assertThat(theBill).isNotNull();
    }

    @Test
    public void arrival_must_publish_RentFinishedEvent()  {
        ArgumentCaptor<RentFinishedEvent> event = ArgumentCaptor.forClass(RentFinishedEvent.class);
        Rent aRent = new Rent(new RentId(), noah, currentDateTime().minusHours(3));
        Bill aBill = new Bill(noah, Duration.ofHours(3), BigDecimal.valueOf(42));
        given(billing.compute(eq(aRent), any())).willReturn(aBill);
        given(rents.getById(aRent.id)).willReturn(aRent);

        Bill theBill = subject.arrival(new RentingTicket(aRent, ark));

        verify(events).publishEvent(event.capture());
        assertThat(event.getValue())
                .hasFieldOrPropertyWithValue("Duration", theBill.getDuration())
                .hasFieldOrPropertyWithValue("Rent", aRent);
    }

    private LocalDateTime currentDateTime() {
        return LocalDateTime.ofInstant(now, clock.getZone());
    }
}
