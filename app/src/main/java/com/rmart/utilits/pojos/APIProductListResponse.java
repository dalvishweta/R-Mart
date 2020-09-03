package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class APIProductListResponse extends BaseResponse {
    @SerializedName("start_index")
    @Expose
    String startIndex;

    @SerializedName("end_index")
    @Expose
    String endIndex;

    @SerializedName("data")
    @Expose
    ArrayList<ProductResponse> productList;

    public String getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(String startIndex) {
        this.startIndex = startIndex;
    }

    public String getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(String endIndex) {
        this.endIndex = endIndex;
    }

    public ArrayList<ProductResponse> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<ProductResponse> productList) {
        this.productList = productList;
    }
}
