package com.rmart.retiler.inventory.Brand.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Brand
{
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("name")
    @Expose
    private String name;

}
