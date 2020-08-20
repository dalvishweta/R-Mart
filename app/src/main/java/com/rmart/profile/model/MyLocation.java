package com.rmart.profile.model;

import android.location.Location;

public class MyLocation {
    // LOCATION DETAILS
    String streetAddress = "Sai temple street, Hanuman Pet, Miryalguda.";
    String shopNo = "18-1814";
    String landMark="Near Sai Temple Street";
    String district = "Nalgonda";
    String state = "Telangana";
    LocationPoints myLocation;

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocationPoints getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(LocationPoints myLocation) {
        this.myLocation = myLocation;
    }
    public void setMyLocation(Double lat, Double lon) {
        this.myLocation.setLatitude(lat);
        this.myLocation.setLatitude(lon);
    }
    public void setMyLocation(String latitude, String longitude) {
        this.myLocation.setLatitude(Double.parseDouble(latitude));
        this.myLocation.setLongitude(Double.parseDouble(longitude));
    }

}
