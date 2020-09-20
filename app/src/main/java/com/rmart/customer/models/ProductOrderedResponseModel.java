package com.rmart.customer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.pojos.BaseResponse;

import java.util.List;

/**
 * Created by Satya Seshu on 19/09/20.
 */
public class ProductOrderedResponseModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    private ProductOrderedResponseDetails productOrderedResponseDetails = null;

    public ProductOrderedResponseDetails getProductOrderedResponseDetails() {
        return productOrderedResponseDetails;
    }

    public void setProductOrderedResponseDetails(ProductOrderedResponseDetails productOrderedResponseDetails) {
        this.productOrderedResponseDetails = productOrderedResponseDetails;
    }
}
