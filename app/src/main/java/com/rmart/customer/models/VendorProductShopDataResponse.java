package com.rmart.customer.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.pojos.BaseResponse;

import java.util.List;

/**
 * Created by Satya Seshu on 10/09/20.
 */
public class VendorProductShopDataResponse extends BaseResponse {

    @SerializedName("shop_mobile_no")
    @Expose
    private String shopMobileNo;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("shop_image")
    @Expose
    private List<String> shopImage = null;
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
    @SerializedName("delivery_charges")
    @Expose
    private Integer deliveryCharges;
    @SerializedName("shop_wishlist_status")
    @Expose
    private Integer shopWishListStatus;

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

    public List<String> getShopImage() {
        return shopImage;
    }

    public void setShopImage(List<String> shopImage) {
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

    public Integer getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(Integer deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public Integer getShopWishListStatus() {
        return shopWishListStatus;
    }

    public void setShopWishListStatus(Integer shopWishListStatus) {
        this.shopWishListStatus = shopWishListStatus;
    }
}
