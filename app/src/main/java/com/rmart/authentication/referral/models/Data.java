package com.rmart.authentication.referral.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Data implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("offer_img")
    @Expose
    private Object offerImg;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getOfferImg() {
        return offerImg;
    }

    public void setOfferImg(Object offerImg) {
        this.offerImg = offerImg;
    }

}
