package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.BaseResponse;

public class UpdateProfileResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    ProfileResponse updateProfile;

    public ProfileResponse getUpdateProfile() {
        return updateProfile;
    }

    public void setUpdateProfile(ProfileResponse updateProfile) {
        this.updateProfile = updateProfile;
    }
}
