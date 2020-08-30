package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    ProfileModel loginData;

    public ProfileModel getLoginData() {
        return loginData;
    }

    public void setLoginData(ProfileModel loginData) {
        this.loginData = loginData;
    }
}
