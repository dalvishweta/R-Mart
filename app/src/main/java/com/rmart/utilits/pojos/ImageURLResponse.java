package com.rmart.utilits.pojos;

import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImageURLResponse extends BaseResponse implements Serializable {

    @SerializedName("id")
    @Expose
    Integer id;

    @SerializedName("display_image")
    @Expose
    String displayImage;

    @SerializedName("image_show")
    @Expose
    String imageShow="";

    @SerializedName("image")
    @Expose
    String imageURL;

    @SerializedName("image_name")
    @Expose
    String imageName;

    @SerializedName("image_id")
    @Expose
    String imageID;

    @SerializedName("isPrimary")
    @Expose
    int isPrimary;

    @SerializedName("image_rawdata")
    @Expose
    String imageRawData;

    private String imageUri;
    private boolean isProductVideoSelected = false;

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

    public int isPrimary() {
        return isPrimary;
    }

    public void setPrimary(int primary) {
        isPrimary = primary;
    }

    public String getImageRawData() {
        return imageRawData;
    }

    public void setImageRawData(String imageRawData) {
        this.imageRawData = imageRawData;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setProductVideoSelected(boolean productVideoSelected) {
        isProductVideoSelected = productVideoSelected;
    }

    public boolean isProductVideoSelected() {
        return isProductVideoSelected;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
