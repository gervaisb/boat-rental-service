package com.github.gervaisb.boatrental.renting.domain;

import lombok.Value;

@Value
public class BoatName {
    public final String name;

    public BoatName(String name) {
        this.name = name;
    }

}
