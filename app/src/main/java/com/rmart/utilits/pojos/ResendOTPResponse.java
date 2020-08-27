package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResendOTPResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    ResendOTP resendOTP;

    public ResendOTP getResendOTP() {
        return resendOTP;
    }

    public void setResendOTP(ResendOTP resendOTP) {
        this.resendOTP = resendOTP;
    }

    class ResendOTP {
        @SerializedName("otp")
        @Expose
        String otp;

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }
    }
}
