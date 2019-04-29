package com.github.gervaisb.boatrental;

import java.time.Clock;

import com.github.gervaisb.boatrental.billing.domain.Bill;
import com.github.gervaisb.boatrental.billing.domain.BillingStrategy;
import com.github.gervaisb.boatrental.billing.domain.ChainedStrategy;
import com.github.gervaisb.boatrental.billing.domain.HourlyRateStrategy;
import com.github.gervaisb.boatrental.billing.domain.WeatherStrategy;
import com.github.gervaisb.boatrental.billing.domain.WorkloadStrategy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BoatRentalServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoatRentalServiceApplication.class, args);
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public BillingStrategy billingStrategy() {
        return new ChainedStrategy(
                new HourlyRateStrategy(5), new WeatherStrategy()
        ).then(new WorkloadStrategy());
    }
}
