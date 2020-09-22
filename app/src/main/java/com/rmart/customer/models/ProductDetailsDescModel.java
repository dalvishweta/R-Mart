package com.rmart.customer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Satya Seshu on 13/09/20.
 */
public class ProductDetailsDescModel implements Serializable {

    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("parent_category_id")
    @Expose
    private Integer parentCategoryId;
    @SerializedName("parent_category_name")
    @Expose
    private String parentCategoryName;
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
    @SerializedName("wishlist_id")
    @Expose
    private String wishListId;

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

    public Boolean getWishList() {
        return isWishList;
    }

    public void setWishList(Boolean wishList) {
        isWishList = wishList;
    }

    public String getWishListId() {
        return wishListId;
    }

    public void setWishListId(String wishListId) {
        this.wishListId = wishListId;
    }

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }

    @NotNull
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("productId", productId).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(productId).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ProductDetailsDescModel)) {
            return false;
        }
        ProductDetailsDescModel rhs = ((ProductDetailsDescModel) other);
        return new EqualsBuilder().append(productId, rhs.productId).isEquals();
    }
}
