package com.rmart.customer.models;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.glied.GlideApp;

import java.io.Serializable;

import androidx.databinding.BindingAdapter;

/**
 * Created by Satya Seshu on 22/09/20.
 */
public class CustomerOrderProductOrderedDetails implements Serializable {

    @SerializedName("cart_id")
    @Expose
    private Integer cartId;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("product_unit_id")
    @Expose
    private Integer productUnitId;
    @SerializedName("unit_number")
    @Expose
    private String unitNumber;
    @SerializedName("total_product_cart_qty")
    @Expose
    private String totalProductCartQty;
    @SerializedName("unit_measure")
    @Expose
    private String unitMeasure;
    @SerializedName("short_unit_measure")
    @Expose
    private String shortUnitMeasure;
    @SerializedName("total_unit_price")
    @Expose
    private String totalUnitPrice;
    @SerializedName("total_selling_price")
    @Expose
    private double totalSellingPrice;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("product_expiry_date")
    @Expose
    private String productExpiryDate;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductUnitId() {
        return productUnitId;
    }

    public void setProductUnitId(Integer productUnitId) {
        this.productUnitId = productUnitId;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getTotalProductCartQty() {
        return totalProductCartQty;
    }

    public void setTotalProductCartQty(String totalProductCartQty) {
        this.totalProductCartQty = totalProductCartQty;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public String getShortUnitMeasure() {
        return shortUnitMeasure;
    }

    public void setShortUnitMeasure(String shortUnitMeasure) {
        this.shortUnitMeasure = shortUnitMeasure;
    }

    public String getTotalUnitPrice() {
        return totalUnitPrice;
    }

    public void setTotalUnitPrice(String totalUnitPrice) {
        this.totalUnitPrice = totalUnitPrice;
    }

    public double getTotalSellingPrice() {
        return totalSellingPrice;
    }

    public void setTotalSellingPrice(double totalSellingPrice) {
        this.totalSellingPrice = totalSellingPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductExpiryDate() {
        return productExpiryDate;
    }

    public void setProductExpiryDate(String productExpiryDate) {
        this.productExpiryDate = productExpiryDate;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String url) {
        GlideApp.with(view.getContext()).load(url).into(view);
    }
}
