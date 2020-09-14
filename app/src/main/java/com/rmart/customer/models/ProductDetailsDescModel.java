package com.rmart.customer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Satya Seshu on 13/09/20.
 */
public class ProductDetailsDescModel implements Serializable {

    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("product_image")
    @Expose
    private List<String> productImage = null;
    @SerializedName("product_expiry_date")
    @Expose
    private String productExpiryDate;
    @SerializedName("product_regional_name")
    @Expose
    private String productRegionalName;
    @SerializedName("product_video_link")
    @Expose
    private String productVideoLink;
    @SerializedName("product_details")
    @Expose
    private String productDetails;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("units")
    @Expose
    private List<CustomerProductsDetailsUnitModel> units = null;
    @SerializedName("is_wishlist")
    @Expose
    private Boolean isWishList;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public List<String> getProductImage() {
        return productImage;
    }

    public void setProductImage(List<String> productImage) {
        this.productImage = productImage;
    }

    public String getProductExpiryDate() {
        return productExpiryDate;
    }

    public void setProductExpiryDate(String productExpiryDate) {
        this.productExpiryDate = productExpiryDate;
    }

    public String getProductRegionalName() {
        return productRegionalName;
    }

    public void setProductRegionalName(String productRegionalName) {
        this.productRegionalName = productRegionalName;
    }

    public String getProductVideoLink() {
        return productVideoLink;
    }

    public void setProductVideoLink(String productVideoLink) {
        this.productVideoLink = productVideoLink;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public List<CustomerProductsDetailsUnitModel> getUnits() {
        return units;
    }

    public void setUnits(List<CustomerProductsDetailsUnitModel> units) {
        this.units = units;
    }

    public Boolean getIsWishList() {
        return isWishList;
    }

    public void setIsWishList(Boolean isWishList) {
        this.isWishList = isWishList;
    }

}
