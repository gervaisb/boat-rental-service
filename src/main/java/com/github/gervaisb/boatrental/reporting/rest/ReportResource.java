package com.github.gervaisb.boatrental.reporting.rest;

import com.github.gervaisb.boatrental.reporting.domain.Report;

import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

class ReportResource extends ResourceSupport {
    public final int completedTrips;
    public final long averageDurationSeconds;

    ReportResource(Report report) {
        averageDurationSeconds = report.getAverageDuration().getSeconds();
        completedTrips = report.getCompletedTrips();
        add(linkTo(methodOn(ReportingController.class).getTodayReport()).withSelfRel());
    }
}
