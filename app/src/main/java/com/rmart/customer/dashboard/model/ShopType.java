package com.rmart.customer.dashboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShopType implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("images")
    @Expose
    private String images;
    @SerializedName("click")
    @Expose
    private Object click;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Object getClick() {
        return click;
    }

    public void setClick(Object click) {
        this.click = click;
    }

}
