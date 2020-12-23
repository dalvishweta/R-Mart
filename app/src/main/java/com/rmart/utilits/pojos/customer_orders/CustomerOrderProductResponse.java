package com.rmart.utilits.pojos.customer_orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.BaseResponse;

public class CustomerOrderProductResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    CustomerOrderProductList productList;

    public CustomerOrderProductList getProductList() {
        return productList;
    }

    public void setProductList(CustomerOrderProductList productList) {
        this.productList = productList;
    }
}
