package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddressResponse extends BaseResponse implements Serializable {
    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("shop_name")
    @Expose
    String shopName;

    @SerializedName("shop_description")
    @Expose
    String shopDescription;

    @SerializedName("gstin_no")
    @Expose
    String gstInNo;

    @SerializedName("latitude")
    @Expose
    String latitude;

    @SerializedName("longitude")
    @Expose
    String longitude;

    @SerializedName("address")
    @Expose
    String address;

    @SerializedName("store_number")
    @Expose
    String store_number;

    @SerializedName("pan_no")
    @Expose
    String pan_no;

    @SerializedName("aadhaar_card_no")
    @Expose
    String aadhaarCardNo;

    @SerializedName("fssai_no")
    @Expose
    String fssaiNo;

    @SerializedName("pincode")
    @Expose
    String pinCode;

    @SerializedName("city")
    @Expose
    String city;

    @SerializedName("state")
    @Expose
    String state;

    @SerializedName("delivery_charges")
    @Expose
    String deliveryCharges;

    @SerializedName("opening_time")
    @Expose
    String openingTime;

    @SerializedName("closing_time")
    @Expose
    String closingTime;

    @SerializedName("delivery_days_after_time")
    @Expose
    String deliveryDaysAfterTime;

    @SerializedName("delivery_radius")
    @Expose
    String deliveryRadius;

    @SerializedName("minimum_order")
    @Expose
    String minimumOrder;

    @SerializedName("created_date")
    @Expose
    String createdDate;

    @SerializedName("created_by")
    @Expose
    String createdBy;

    @SerializedName("updated_date")
    @Expose
    String updatedDate;

    @SerializedName("updated_by")
    @Expose
    String updatedBY;

    @SerializedName("is_active")
    @Expose
    String isActive;

    @SerializedName("delivery_days_before_time")
    @Expose
    String deliveryDaysBeforeTime;

    @SerializedName("client_id")
    @Expose
    String clientID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopDescription() {
        return shopDescription;
    }

    public void setShopDescription(String shopDescription) {
        this.shopDescription = shopDescription;
    }

    public String getGstInNo() {
        return gstInNo;
    }

    public void setGstInNo(String gstInNo) {
        this.gstInNo = gstInNo;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStore_number() {
        return store_number;
    }

    public void setStore_number(String store_number) {
        this.store_number = store_number;
    }

    public String getPan_no() {
        return pan_no;
    }

    public void setPan_no(String pan_no) {
        this.pan_no = pan_no;
    }

    public String getAadhaarCardNo() {
        return aadhaarCardNo;
    }

    public void setAadhaarCardNo(String aadhaarCardNo) {
        this.aadhaarCardNo = aadhaarCardNo;
    }

    public String getFssaiNo() {
        return fssaiNo;
    }

    public void setFssaiNo(String fssaiNo) {
        this.fssaiNo = fssaiNo;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(String deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String getDeliveryDaysAfterTime() {
        return deliveryDaysAfterTime;
    }

    public void setDeliveryDaysAfterTime(String deliveryDaysAfterTime) {
        this.deliveryDaysAfterTime = deliveryDaysAfterTime;
    }

    public String getDeliveryRadius() {
        return deliveryRadius;
    }

    public void setDeliveryRadius(String deliveryRadius) {
        this.deliveryRadius = deliveryRadius;
    }

    public String getMinimumOrder() {
        return minimumOrder;
    }

    public void setMinimumOrder(String minimumOrder) {
        this.minimumOrder = minimumOrder;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBY() {
        return updatedBY;
    }

    public void setUpdatedBY(String updatedBY) {
        this.updatedBY = updatedBY;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getDeliveryDaysBeforeTime() {
        return deliveryDaysBeforeTime;
    }

    public void setDeliveryDaysBeforeTime(String deliveryDaysBeforeTime) {
        this.deliveryDaysBeforeTime = deliveryDaysBeforeTime;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }
}
