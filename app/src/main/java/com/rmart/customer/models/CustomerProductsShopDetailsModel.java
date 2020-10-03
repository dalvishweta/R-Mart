package com.rmart.customer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Created by Satya Seshu on 08/09/20.
 */
public class CustomerProductsShopDetailsModel implements Serializable {

    @SerializedName("shop_mobile_no")
    @Expose
    private String shopMobileNo;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("shop_image")
    @Expose
    private String shopImage;
    @SerializedName("shop_address")
    @Expose
    private String shopAddress;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("shop_del_chrg")
    @Expose
    private String shopDelChrg;
    @SerializedName("min_order_amt")
    @Expose
    private Integer minOrderAmt;
    @SerializedName("delivery_days")
    @Expose
    private String deliveryDays;
    @SerializedName("shop_id")
    @Expose
    private Integer shopId;
    @SerializedName("client_id")
    @Expose
    private Integer clientId;
    @SerializedName("shop_wishlist_status")
    @Expose
    private Integer shopWishListStatus = 0;
    @SerializedName("shop_wishlist_id")
    @Expose
    private Integer shopWishListId = -1;
    @SerializedName("latitude")
    @Expose
    private Double latitude;

    @SerializedName("longitude")
    @Expose
    private Double longitude;

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

    public String getShopDelChrg() {
        return shopDelChrg;
    }

    public void setShopDelChrg(String shopDelChrg) {
        this.shopDelChrg = shopDelChrg;
    }

    public Integer getMinOrderAmt() {
        return minOrderAmt;
    }

    public void setMinOrderAmt(Integer minOrderAmt) {
        this.minOrderAmt = minOrderAmt;
    }

    public String getDeliveryDays() {
        return deliveryDays;
    }

    public void setDeliveryDays(String deliveryDays) {
        this.deliveryDays = deliveryDays;
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

    public Integer getShopWishListStatus() {
        return shopWishListStatus;
    }

    public void setShopWishListStatus(Integer shopWishListStatus) {
        this.shopWishListStatus = shopWishListStatus;
    }

    public Integer getShopWishListId() {
        return shopWishListId;
    }

    public void setShopWishListId(Integer shopWishListId) {
        this.shopWishListId = shopWishListId;
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
        if (!(other instanceof CustomerProductsShopDetailsModel)) {
            return false;
        }
        CustomerProductsShopDetailsModel rhs = ((CustomerProductsShopDetailsModel) other);
        return new EqualsBuilder().append(shopId, rhs.shopId).isEquals();
    }

}
