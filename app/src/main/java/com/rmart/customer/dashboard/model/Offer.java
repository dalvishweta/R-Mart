package com.rmart.customer.dashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offer {

    @SerializedName("images")
    @Expose
    private String images;

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
