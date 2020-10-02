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


    @SerializedName("images")
    @Expose
    ArrayList<ImageURLResponse> imageDataObject = new ArrayList<>();

    @SerializedName("product_lib_id")
    @Expose
    String productLibID;

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

    @SerializedName("product_regional_name")
    @Expose
    String regionalName;

    @SerializedName("product_video_link")
    @Expose
    String videoLInk;

    @SerializedName("product_details")
    @Expose
    String productDetails;

    @SerializedName("display_image")
    @Expose
    String displayImage;

    @SerializedName("product_cat_id")
    @Expose
    String productCatID;

    @SerializedName("units")
    @Expose
    ArrayList<UnitObject> unitObjects = new ArrayList<>();

    @SerializedName("brand")
    @Expose
    String brand;


    @SerializedName("brand_id")
    @Expose
    String brandID;


    @SerializedName("brand_name")
    @Expose
    String brandName;
    String subCategory;
    String expiry_date;
    String delivery_days;

    public ProductResponse() {

    }


    public ProductResponse(ProductResponse product) {
        productID = product.productID;
        productImage = product.productImage;
        name = product.name;
        description = product.description;
        categoryID = product.categoryID;
        // brandID = product.brandID;
        subCategory = product.subCategory;
        brand = product.brand;
        brandID = product.brandID;
        brandName = product.brandName;
        regionalName = product.regionalName;
        videoLInk = product.videoLInk;
        expiry_date = product.expiry_date;
        delivery_days = product.delivery_days;
        productLibID = product.productLibID;
        imageDataObject = new ArrayList<>();
        imageDataObject.addAll(product.getImageDataObject());
        unitObjects = new ArrayList<>();
        unitObjects.addAll(product.getUnitObjects());
    }

/*    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }*/

    public String getProductLibID() {
        return productLibID;
    }

    public void setProductLibID(String productLibID) {
        this.productLibID = productLibID;
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

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getDelivery_days() {
        return delivery_days;
    }

    public void setDelivery_days(String delivery_days) {
        this.delivery_days = delivery_days;
    }

    public ArrayList<ImageURLResponse> getImageDataObject() {
        return imageDataObject;
    }

    public void setImageDataObject(ArrayList<ImageURLResponse> imageDataObject) {
        this.imageDataObject = imageDataObject;
    }

    public ArrayList<UnitObject> getUnitObjects() {
        return unitObjects;
    }

    public void setUnitObjects(ArrayList<UnitObject> unitObjects) {
        this.unitObjects = unitObjects;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public String getDisplayImage() {
        return displayImage;
    }

    public void setDisplayImage(String displayImage) {
        this.displayImage = displayImage;
    }

    public String getProductCatID() {
        return productCatID;
    }

    public void setProductCatID(String productCatID) {
        this.productCatID = productCatID;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
