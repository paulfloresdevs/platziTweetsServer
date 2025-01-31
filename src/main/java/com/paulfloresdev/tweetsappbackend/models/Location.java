package com.paulfloresdev.tweetsappbackend.models;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Location {

    private Double altitude;
    private Double longitude;

    public Location(Double altitude, Double longitude) {
        this.altitude = altitude;
        this.longitude = longitude;
    }

    public Location() {

    }
}
