package com.github.gervaisb.boatrental.reporting.rest;

import com.github.gervaisb.boatrental.reporting.domain.ReportingService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportingController {

    private final ReportingService service;

    public ReportingController(ReportingService service) {
        this.service = service;
    }

    @GetMapping(value = "/today", produces = "application/json")
    public ReportResource getTodayReport() {
        return new ReportResource(service.getTodayReport());
    }

}
