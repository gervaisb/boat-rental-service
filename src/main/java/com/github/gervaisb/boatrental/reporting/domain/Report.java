package com.github.gervaisb.boatrental.reporting.domain;

import java.time.Duration;

import lombok.Value;

@Value
public class Report {

    private final int completedTrips;
    private final Duration averageDuration;

}
