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
 * Created by Satya Seshu on 17/09/20.
 */
public class ShopWiseWishListResponseDetails implements Serializable {

    @SerializedName("wishlist_id")
    @Expose
    private Integer wishListId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("product_expiry_date")
    @Expose
    private String productExpiryDate;
    @SerializedName("units")
    @Expose
    private List<CustomerProductsDetailsUnitModel> units = null;

    public Integer getWishListId() {
        return wishListId;
    }

    public void setWishListId(Integer wishListId) {
        this.wishListId = wishListId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductExpiryDate() {
        return productExpiryDate;
    }

    public void setProductExpiryDate(String productExpiryDate) {
        this.productExpiryDate = productExpiryDate;
    }

    public List<CustomerProductsDetailsUnitModel> getUnits() {
        return units;
    }

    public void setUnits(List<CustomerProductsDetailsUnitModel> units) {
        this.units = units;
    }

    @NotNull
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("wishListId", wishListId).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(wishListId).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ShopWiseWishListResponseDetails)) {
            return false;
        }
        ShopWiseWishListResponseDetails rhs = ((ShopWiseWishListResponseDetails) other);
        return new EqualsBuilder().append(wishListId, rhs.wishListId).isEquals();
    }
}
