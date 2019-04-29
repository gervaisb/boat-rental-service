package com.github.gervaisb.boatrental.renting.domain;

import java.time.Duration;

import lombok.EqualsAndHashCode;
import lombok.Value;

import org.springframework.context.ApplicationEvent;

@EqualsAndHashCode(callSuper = true)
@Value
public class RentFinishedEvent extends ApplicationEvent {
    private final Rent rent;
    private final Duration duration;

    public RentFinishedEvent(Object source, Rent rent, Duration duration) {
        super(source);
        this.rent = rent;
        this.duration = duration;
    }
}
