package com.rmart.retiler.product.model.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("image")
    @Expose
    String image;
    @SerializedName("name")
    @Expose
    String name;

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
