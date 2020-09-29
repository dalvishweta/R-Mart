package com.rmart.customer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.pojos.BaseResponse;

/**
 * Created by Satya Seshu on 27/09/20.
 */
public class RSAKeyResponseModel extends BaseResponse {

    @SerializedName("data")
    @Expose
    private RSAKeyDataResponseModel rsaKeyDataResponseModel;

    public RSAKeyDataResponseModel getRsaKeyDataResponseModel() {
        return rsaKeyDataResponseModel;
    }

    public void setRsaKeyDataResponseModel(RSAKeyDataResponseModel rsaKeyDataResponseModel) {
        this.rsaKeyDataResponseModel = rsaKeyDataResponseModel;
    }

    public static class RSAKeyDataResponseModel {

        @SerializedName("ccavenue_data")
        @Expose
        private RSAKeyResponseDetails rsaKeyResponseDetails;

        @SerializedName("order_message")
        @Expose
        private String orderMessage;

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
    }
}
