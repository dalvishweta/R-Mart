package com.rmart.customer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.BaseResponse;

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

    public static class ProductOrderedResponseDetails {

        @SerializedName("ccavenue_data")
        @Expose
        private RSAKeyResponseDetails rsaKeyResponseDetails;

        @SerializedName("order_message")
        @Expose
        private String orderMessage;

        @SerializedName("total_cart_count")
        @Expose
        private int totalCartCount = 0;

        public RSAKeyResponseDetails getRsaKeyResponseDetails() {
            return rsaKeyResponseDetails;
        }

        public void setRsaKeyResponseDetails(RSAKeyResponseDetails rsaKeyResponseDetails) {
            this.rsaKeyResponseDetails = rsaKeyResponseDetails;
        }

        public String getOrderMessage() {
            return orderMessage;
        }

        public void setOrderMessage(String orderMessage) {
            this.orderMessage = orderMessage;
        }

        public int getTotalCartCount() {
            return totalCartCount;
        }

        public void setTotalCartCount(int totalCartCount) {
            this.totalCartCount = totalCartCount;
        }
    }
}
