package com.rmart.customer.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Satya Seshu on 10/09/20.
 */
public class ProductBaseModel implements Serializable {

    private String productCategoryName;
    private int productCategoryId;
    private List<VendorProductDataResponse> productsList;

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public int getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public List<VendorProductDataResponse> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<VendorProductDataResponse> productsList) {
        this.productsList = productsList;
    }

    @NotNull
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("productCategoryName", productCategoryName).append("productCategoryId", productCategoryId).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(productCategoryName).append(productCategoryId).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ProductBaseModel)) {
            return false;
        }
        ProductBaseModel rhs = ((ProductBaseModel) other);
        return new EqualsBuilder().append(productCategoryName, rhs.productCategoryName).append(productCategoryId, rhs.productCategoryId).isEquals();
    }
}
