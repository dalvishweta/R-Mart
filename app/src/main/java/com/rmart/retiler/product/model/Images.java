package com.rmart.retiler.product.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Images {
    @SerializedName("image_id")
    @Expose
    int imageId;
    @SerializedName("image_name")
    @Expose
    String imageName;
    @SerializedName("isPrimary")
    @Expose
    int isPrimary;
    @SerializedName("image")
    @Expose
    String image;
}
