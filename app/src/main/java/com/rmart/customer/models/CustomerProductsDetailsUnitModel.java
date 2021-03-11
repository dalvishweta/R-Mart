package com.rmart.customer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.lifecycle.MutableLiveData;

/**
 * Created by Satya Seshu on 08/09/20.
 */
public class CustomerProductsDetailsUnitModel implements Serializable {

    public boolean selected = false;
    @SerializedName("product_unit_id")
    @Expose
    private Integer productUnitId;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("unit_price")
    @Expose
    private Double unitPrice;
    @SerializedName("unit_number")
    @Expose
    private String unitNumber;
    @SerializedName("selling_price")
    @Expose
    private Double sellingPrice;
    @SerializedName("unit_measure")
    @Expose
    private String unitMeasure;
    @SerializedName("product_unit_quantity")
    @Expose
    private Integer productUnitQuantity;
    @SerializedName("short_unit_measure")
    @Expose
    private String shortUnitMeasure;
    @SerializedName("product_discount")
    @Expose
    private Integer productDiscount;

    @SerializedName("total_product_cart_qty")
    @Expose
    private Integer totalProductCartQty;

    public Integer getProductUnitId() {
        return productUnitId;
    }

    public void setProductUnitId(Integer productUnitId) {
        this.productUnitId = productUnitId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public Integer getProductUnitQuantity() {
        return productUnitQuantity;
    }

    public void setProductUnitQuantity(Integer productUnitQuantity) {
        this.productUnitQuantity = productUnitQuantity;
    }

    public String getShortUnitMeasure() {
        return shortUnitMeasure;
    }

    public void setShortUnitMeasure(String shortUnitMeasure) {
        this.shortUnitMeasure = shortUnitMeasure;
    }

    public Integer getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(Integer productDiscount) {
        this.productDiscount = productDiscount;
    }

    public Integer getTotalProductCartQty() {
        return totalProductCartQty;
    }

    public void setTotalProductCartQty(Integer totalProductCartQty) {
        this.totalProductCartQty = totalProductCartQty;
    }
}
