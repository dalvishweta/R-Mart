package com.rmart.utilits.pojos.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {

    @SerializedName("quantity")
    @Expose
    private Integer quantity;

    @SerializedName("unit_id")
    @Expose
    private Integer unitId;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("product_id")
    @Expose
    private String productID;

    @SerializedName("unit_measure")
    @Expose
    private String unitMeasure;

    @SerializedName("unit")
    @Expose
    private String unit;

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("product_name")
    @Expose
    private String productName;

    @SerializedName("display_image")
    @Expose
    private String displayImage;

    @SerializedName("available_quantity")
    @Expose
    private Integer availableQuantity;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDisplayImage() {
        return displayImage;
    }

    public void setDisplayImage(String displayImage) {
        this.displayImage = displayImage;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }
}
