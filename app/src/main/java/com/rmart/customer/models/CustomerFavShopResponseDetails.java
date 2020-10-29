package com.rmart.customer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Created by Satya Seshu on 30/10/20.
 */
public class CustomerFavShopResponseDetails implements Serializable {

    @SerializedName("shop_mobile_no")
    @Expose
    private String shopMobileNo;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("email_id")
    @Expose
    private String emailId;
    @SerializedName("shop_image")
    @Expose
    private String shopImage;
    @SerializedName("shop_address")
    @Expose
    private String shopAddress;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("min_order_amt")
    @Expose
    private Integer minOrderAmt;
    @SerializedName("delivery_days_after_time")
    @Expose
    private String deliveryDaysAfterTime;
    @SerializedName("delivery_days_before_time")
    @Expose
    private String deliveryDaysBeforeTime;
    @SerializedName("shop_id")
    @Expose
    private Integer shopId;
    @SerializedName("client_id")
    @Expose
    private Integer clientId;
    @SerializedName("delivery_charges")
    @Expose
    private String deliveryCharges;
    @SerializedName("closing_time")
    @Expose
    private String closingTime;
    @SerializedName("opening_time")
    @Expose
    private String openingTime;
    @SerializedName("shop_latitude")
    @Expose
    private String shopLatitude;
    @SerializedName("shop_longitude")
    @Expose
    private String shopLongitude;
    @SerializedName("shop_wishlist_id")
    @Expose
    private Integer shopWishListId;

    public String getShopMobileNo() {
        return shopMobileNo;
    }

    public void setShopMobileNo(String shopMobileNo) {
        this.shopMobileNo = shopMobileNo;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getMinOrderAmt() {
        return minOrderAmt;
    }

    public void setMinOrderAmt(Integer minOrderAmt) {
        this.minOrderAmt = minOrderAmt;
    }

    public String getDeliveryDaysAfterTime() {
        return deliveryDaysAfterTime;
    }

    public void setDeliveryDaysAfterTime(String deliveryDaysAfterTime) {
        this.deliveryDaysAfterTime = deliveryDaysAfterTime;
    }

    public String getDeliveryDaysBeforeTime() {
        return deliveryDaysBeforeTime;
    }

    public void setDeliveryDaysBeforeTime(String deliveryDaysBeforeTime) {
        this.deliveryDaysBeforeTime = deliveryDaysBeforeTime;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(String deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getShopLatitude() {
        return shopLatitude;
    }

    public void setShopLatitude(String shopLatitude) {
        this.shopLatitude = shopLatitude;
    }

    public String getShopLongitude() {
        return shopLongitude;
    }

    public void setShopLongitude(String shopLongitude) {
        this.shopLongitude = shopLongitude;
    }

    public Integer getShopWishListId() {
        return shopWishListId;
    }

    public void setShopWishListId(Integer shopWishListId) {
        this.shopWishListId = shopWishListId;
    }

    @NotNull
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("shopId", shopId).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(shopId).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof CustomerFavShopResponseDetails)) {
            return false;
        }
        CustomerFavShopResponseDetails rhs = ((CustomerFavShopResponseDetails) other);
        return new EqualsBuilder().append(shopId, rhs.shopId).isEquals();
    }
}
