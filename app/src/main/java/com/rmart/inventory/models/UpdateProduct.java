package com.rmart.inventory.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateProduct{
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("is_active")
    @Expose
    private String is_active;
    @SerializedName("vendor_active")
    @Expose
    private String vendor_active;

}
