package com.rmart.retiler.inventory.category.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category
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
