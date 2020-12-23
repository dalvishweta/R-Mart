package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.customer.models.RSAKeyResponseDetails;
import com.rmart.utilits.BaseResponse;

public class RegistrationResponse extends BaseResponse {

    @SerializedName("otp")
    @Expose
    private String otp = "";

    @SerializedName("ccavenue_data")
    @Expose
    RSAKeyResponseDetails rsaKeyResponseDetails;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public RSAKeyResponseDetails getRsaKeyResponseDetails() {
        return rsaKeyResponseDetails;
    }

    public void setRsaKeyResponseDetails(RSAKeyResponseDetails rsaKeyResponseDetails) {
        this.rsaKeyResponseDetails = rsaKeyResponseDetails;
    }
}
