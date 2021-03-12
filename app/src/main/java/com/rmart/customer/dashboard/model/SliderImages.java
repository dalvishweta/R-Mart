package com.rmart.customer.dashboard.model;

import com.google.gson.annotations.SerializedName;

public class SliderImages {
    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getLinktoredirect() {
        return linktoredirect;
    }
    @SerializedName("image")
    String image;
    @SerializedName("linktoredirect")
    String linktoredirect;






    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



}