package com.rmart.customer.shops_new.home.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceOffer {
    @SerializedName("service_caption")
    @Expose
    private String serviceCaption;
    @SerializedName("name_image")
    @Expose
    private String nameImage;

    public String getServiceCaption() {
        return serviceCaption;
    }

    public void setServiceCaption(String serviceCaption) {
        this.serviceCaption = serviceCaption;
    }

    public String getNameImage() {
        return nameImage;
    }

    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
    }
}