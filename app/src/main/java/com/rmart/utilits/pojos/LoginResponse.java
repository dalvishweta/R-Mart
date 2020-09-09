package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    ProfileResponse loginData;

    public ProfileResponse getLoginData() {
        return loginData;
    }

    public void setLoginData(ProfileResponse loginData) {
        this.loginData = loginData;
    }
}
