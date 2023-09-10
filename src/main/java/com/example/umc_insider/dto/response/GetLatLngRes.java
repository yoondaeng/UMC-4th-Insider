package com.example.umc_insider.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetLatLngRes {
    private double latitude;
    private double longitude;

    public GetLatLngRes(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // getter 및 setter 메소드

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
