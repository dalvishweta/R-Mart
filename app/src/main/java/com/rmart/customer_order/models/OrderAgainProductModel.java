package com.rmart.customer_order.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Satya Seshu on 10/10/20.
 */
public class OrderAgainProductModel implements Serializable {

    @SerializedName("product_unit_id")
    @Expose
    private Integer productUnitId;
    @SerializedName("product_quantity")
    @Expose
    private Integer productQuantity;

    public Integer getProductUnitId() {
        return productUnitId;
    }

    public void setProductUnitId(Integer productUnitId) {
        this.productUnitId = productUnitId;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }
}
