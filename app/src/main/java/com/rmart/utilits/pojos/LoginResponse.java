package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.customer.models.RSAKeyResponseDetails;

public class LoginResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    ProfileResponse loginData;

    @SerializedName("payment_data")
    @Expose
    RSAKeyResponseDetails paymentData;

    public ProfileResponse getLoginData() {
        return loginData;
    }

    public void setLoginData(ProfileResponse loginData) {
        this.loginData = loginData;
    }

    public RSAKeyResponseDetails getPaymentData() {
        return paymentData;
    }

    public void setPaymentData(RSAKeyResponseDetails paymentData) {
        this.paymentData = paymentData;
    }
}
