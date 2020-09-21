package com.rmart.utilits.pojos;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageURLResponse extends BaseResponse {
    @SerializedName("display_image")
    @Expose
    String displayImage;

    @SerializedName("image_show")
    @Expose
    String imageShow;

    @SerializedName("image")
    @Expose
    String imageURL;

    @SerializedName("image_id")
    @Expose
    String imageID;

    @SerializedName("isPrimary")
    @Expose
    boolean isPrimary;
    @SerializedName("image_rawdata")
    @Expose
    String imageRawData;

    private Bitmap productImageBitmap;

    public String getDisplayImage() {
        return displayImage;
    }

    public void setDisplayImage(String displayImage) {
        this.displayImage = displayImage;
    }

    public String getImageShow() {
        return imageShow;
    }

    public void setImageShow(String imageShow) {
        this.imageShow = imageShow;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public String getImageRawData() {
        return imageRawData;
    }

    public void setImageRawData(String imageRawData) {
        this.imageRawData = imageRawData;
    }

    public Bitmap getProductImageBitmap() {
        return productImageBitmap;
    }

    public void setProductImageBitmap(Bitmap productImageBitmap) {
        this.productImageBitmap = productImageBitmap;
    }
}
