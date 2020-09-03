package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RetailerAddress {

    @SerializedName("shop_name")
    @Expose
    String shopName;

    @SerializedName("pan_no")
    @Expose
    String panNO;

    @SerializedName("gstin_no")
    @Expose
    String gstNO;

    @SerializedName("address")
    @Expose
    String address;

    @SerializedName("store_number")
    @Expose
    String storeNumber;

    @SerializedName("state")
    @Expose
    String state;

    @SerializedName("latitude")
    @Expose
    String latitude;

    @SerializedName("longitude")
    @Expose
    String longitude;

    @SerializedName("pinode")
    @Expose
    String pinCode;

    @SerializedName("city")
    @Expose
    String city;

    @SerializedName("delivery_radius")
    @Expose
    String deliveryRadius;

    @SerializedName("created_by")
    @Expose
    String createdBY;

    @SerializedName("updated_by")
    @Expose
    String updatedBY;

    @SerializedName("client_id")
    @Expose
    String clientID;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPanNO() {
        return panNO;
    }

    public void setPanNO(String panNO) {
        this.panNO = panNO;
    }

    public String getGstNO() {
        return gstNO;
    }

    public void setGstNO(String gstNO) {
        this.gstNO = gstNO;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStoreNumber() {
        return storeNumber;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDeliveryRadius() {
        return deliveryRadius;
    }

    public void setDeliveryRadius(String deliveryRadius) {
        this.deliveryRadius = deliveryRadius;
    }

    public String getCreatedBY() {
        return createdBY;
    }

    public void setCreatedBY(String createdBY) {
        this.createdBY = createdBY;
    }

    public String getUpdatedBY() {
        return updatedBY;
    }

    public void setUpdatedBY(String updatedBY) {
        this.updatedBY = updatedBY;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }
}
