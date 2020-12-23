package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.BaseResponse;

public class APIBrandResponse extends BaseResponse {

    @SerializedName("updated_by")
    @Expose
    String updatedBY;

    @SerializedName("updatedDate")
    @Expose
    String updatedDate;

    @SerializedName("is_active")
    @Expose
    String isActive;

    @SerializedName("id")
    @Expose
    String brandID;

    @SerializedName("brand_name")
    @Expose
    String brandName;

    @SerializedName("created_date")
    @Expose
    String createdDate;

    @SerializedName("created_by")
    @Expose
    String createdBY;

    public String getUpdatedBY() {
        return updatedBY;
    }

    public void setUpdatedBY(String updatedBY) {
        this.updatedBY = updatedBY;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBY() {
        return createdBY;
    }

    public void setCreatedBY(String createdBY) {
        this.createdBY = createdBY;
    }
}
