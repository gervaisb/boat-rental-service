package com.github.gervaisb.boatrental.renting.domain;

import java.io.Serializable;
import java.util.UUID;

import lombok.Value;

@Value
public class RentId implements Serializable {
    public final String value;

    public RentId() {
        this("R-"+UUID.randomUUID().toString());
    }

    public RentId(String value) {
        this.value = value;
    }
}
