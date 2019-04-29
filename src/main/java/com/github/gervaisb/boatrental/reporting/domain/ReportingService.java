package com.github.gervaisb.boatrental.reporting.domain;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Set;
import java.util.List;

import com.github.gervaisb.boatrental.renting.domain.RentFinishedEvent;
import lombok.Value;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.averagingLong;
import static java.util.stream.Collectors.toSet;

@Service
public class ReportingService implements ApplicationListener<RentFinishedEvent> {

    private final List<Metric> metrics = new LinkedList<>();

    public Report getTodayReport() {
        Set<Metric> rentsOfTheDay = getFinishedRentsSince(thisMorning());
        long averageInMillis = rentsOfTheDay.stream()
                .map(Metric::getDuration)
                .collect(averagingLong(Duration::toMillis))
                .longValue();

        return new Report(rentsOfTheDay.size(), Duration.ofMillis(averageInMillis));
    }

    private Set<Metric> getFinishedRentsSince(LocalDateTime dateTime) {
        return metrics.stream()
                .filter(m -> m.isAfter(dateTime))
                .collect(toSet());
    }

    private LocalDateTime thisMorning() {
        return LocalDate.now().atTime(0, 1);
    }


    @Override
    public void onApplicationEvent(RentFinishedEvent event) {
        metrics.add(new Metric(event.getRent().getStart(), event.getDuration()));
    }

    @Value
    class Metric {
        private final LocalDateTime start;
        private final Duration duration;

        boolean isAfter(LocalDateTime dateTime) {
            return start.isAfter(dateTime);
        }
    }
}
