package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.inventory.models.UnitObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductResponse extends BaseResponse implements Serializable {
    @SerializedName("product_id")
    @Expose
    String productID;

    @SerializedName("product_image")
    @Expose
    String productImage;

    @SerializedName("product_name")
    @Expose
    String name;

    @SerializedName("product_desc")
    @Expose
    String description;

    @SerializedName("category_id")
    @Expose
    String categoryID;

    String subCategory;
    String brandName;

    @SerializedName("brand")
    @Expose
    String brandID;

    @SerializedName("product_regional_name")
    @Expose
    String regionalName;

    @SerializedName("product_video_link")
    @Expose
    String videoLInk;

    String expiryDate;
    String deliveryInDays;
    ArrayList<String> images = new ArrayList<>();
    @SerializedName("units")
    @Expose
    ArrayList<UnitObject> unitObjects = new ArrayList<>();

    public ProductResponse() {

    }


    public ProductResponse(ProductResponse product) {
        productID = product.productID;
        productImage = product.productImage;
        name = product.name;
        description = product.description;
        categoryID = product.categoryID;
        brandID = product.brandID;
        subCategory = product.subCategory;;
        brandName = product.brandName;
        regionalName = product.regionalName;
        videoLInk = product.videoLInk;
        expiryDate = product.expiryDate;
        deliveryInDays = product.deliveryInDays;
        images = new ArrayList<>();
        images.addAll(product.getImages());
        unitObjects = new ArrayList<>();
        unitObjects.addAll(product.getUnitObjects());
    }

    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getRegionalName() {
        return regionalName;
    }

    public void setRegionalName(String regionalName) {
        this.regionalName = regionalName;
    }

    public String getVideoLInk() {
        return videoLInk;
    }

    public void setVideoLInk(String videoLInk) {
        this.videoLInk = videoLInk;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getDeliveryInDays() {
        return deliveryInDays;
    }

    public void setDeliveryInDays(String deliveryInDays) {
        this.deliveryInDays = deliveryInDays;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<UnitObject> getUnitObjects() {
        return unitObjects;
    }

    public void setUnitObjects(ArrayList<UnitObject> unitObjects) {
        this.unitObjects = unitObjects;
    }
}
