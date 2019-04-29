package com.github.gervaisb.boatrental.reporting.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import com.github.gervaisb.boatrental.renting.domain.Customer;
import com.github.gervaisb.boatrental.renting.domain.Rent;
import com.github.gervaisb.boatrental.renting.domain.RentFinishedEvent;
import com.github.gervaisb.boatrental.renting.domain.RentId;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Blaise Gervais
 */
public class ReportingServiceTest {

    private final ReportingService subject = new ReportingService();

    @Test
    public void getTodayReport_must_produce_a_report_of_finished_rents() {
        given(  rentFinished(Duration.ofHours(2)),
                rentFinished(Duration.ofHours(3)),
                rentFinished(Duration.ofHours(4))
                );

        Report report = subject.getTodayReport();

        assertThat(report)
                .hasFieldOrPropertyWithValue("CompletedTrips", 3)
                .hasFieldOrPropertyWithValue("AverageDuration", Duration.ofHours(3));
    }

    private RentFinishedEvent rentFinished(Duration duration) {
        LocalDateTime start = LocalDateTime.now()
                .plusSeconds(ThreadLocalRandom.current().nextInt(120))
                .minusSeconds(duration.getSeconds());
        return new RentFinishedEvent(
                this,
                new Rent(new RentId(), new Customer("Fake", "Customer"), start),
                duration
        );
    }

    private void given(RentFinishedEvent... events) {
        for (RentFinishedEvent event : events) {
            subject.onApplicationEvent(event);
        }
    }
}
