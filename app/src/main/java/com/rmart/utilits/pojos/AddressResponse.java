package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.BaseResponse;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class AddressResponse extends BaseResponse implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("shop_name")
    @Expose
    private String shopName;

    @SerializedName("shop_description")
    @Expose
    private String shopDescription;

    @SerializedName("gstin_no")
    @Expose
    private String gstInNo;

    @SerializedName("shop_act")
    @Expose
    private String shopACT;

    @SerializedName("latitude")
    @Expose
    private double latitude;

    @SerializedName("longitude")
    @Expose
    private double longitude;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("store_number")
    @Expose
    private String store_number;

    @SerializedName("pan_no")
    @Expose
    private String pan_no;

    @SerializedName("aadhaar_card_no")
    @Expose
    private String aadhaarCardNo;

    @SerializedName("fssai_no")
    @Expose
    private String fssaiNo;

    @SerializedName("pincode")
    @Expose
    private String pinCode;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("state")
    @Expose
    private String state;

    @SerializedName("delivery_charges")
    @Expose
    private String deliveryCharges;

    @SerializedName("opening_time")
    @Expose
    private String openingTime;

    @SerializedName("closing_time")
    @Expose
    private String closingTime;

    @SerializedName("delivery_days_after_time")
    @Expose
    private String deliveryDaysAfterTime;

    @SerializedName("delivery_days_before_time")
    @Expose
    private String deliveryDaysBeforeTime;

    @SerializedName("delivery_radius")
    @Expose
    private String deliveryRadius;

    @SerializedName("minimum_order")
    @Expose
    private String minimumOrder;

    @SerializedName("created_date")
    @Expose
    private String createdDate;

    @SerializedName("created_by")
    @Expose
    private String createdBy;

    @SerializedName("updated_date")
    @Expose
    private String updatedDate;

    @SerializedName("updated_by")
    @Expose
    private String updatedBY;

    @SerializedName("is_active")
    @Expose
    private Integer isActive;

    @SerializedName("client_id")
    @Expose
    private String clientID;

    @SerializedName("aadhar_front_image")
    @Expose
    private String aadharFrontImage;

    @SerializedName("pancard_image")
    @Expose
    private String panCardImage;

    @SerializedName("aadhar_back_image")
    @Expose
    private String aadharBackImage;

    @SerializedName("shop_image")
    @Expose
    private String shopImage;

    private boolean isPrimaryAddress = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
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

    public boolean isPrimaryAddress() {
        return isPrimaryAddress;
    }

    public void setPrimaryAddress(boolean primaryAddress) {
        isPrimaryAddress = primaryAddress;
    }

    public String getAadharFrontImage() {
        return aadharFrontImage;
    }

    public void setAadharFrontImage(String aadharFrontImage) {
        this.aadharFrontImage = aadharFrontImage;
    }

    public String getPanCardImage() {
        return panCardImage;
    }

    public void setPanCardImage(String panCardImage) {
        this.panCardImage = panCardImage;
    }

    public String getAadharBackImage() {
        return aadharBackImage;
    }

    public void setAadharBackImage(String aadharBackImage) {
        this.aadharBackImage = aadharBackImage;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

    @NotNull
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddressResponse)) {
            return false;
        }
        AddressResponse rhs = ((AddressResponse) other);
        return new EqualsBuilder().append(id, rhs.id).isEquals();
    }

    public String getShopACT() {
        return shopACT;
    }

    public void setShopACT(String shopACT) {
        this.shopACT = shopACT;
    }
}
