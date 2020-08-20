package com.rmart.profile.model;

import java.io.Serializable;

public class LocationPoints implements Serializable {
    Double latitude;
    Double longitude;

    public LocationPoints(Double lat, Double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }

    LocationPoints(String lat, String lon) {
        this.latitude = Double.parseDouble(lat);
        this.longitude = Double.parseDouble(lon);
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
