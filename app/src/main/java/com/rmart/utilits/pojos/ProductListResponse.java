package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.BaseResponse;

import java.util.ArrayList;

public class ProductListResponse extends BaseResponse {
    @SerializedName("start_index")
    @Expose
    String startIndex;

    @SerializedName("end_index")
    @Expose
    String endIndex;

    @SerializedName("product_image_path")
    @Expose
    String productImagePath;

    @SerializedName("product_count")
    @Expose
    String productCount;

    @SerializedName("data")
    @Expose
    ArrayList<ProductResponse> productResponses;

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

    public String getProductImagePath() {
        return productImagePath;
    }

    public void setProductImagePath(String productImagePath) {
        this.productImagePath = productImagePath;
    }

    public String getProductCount() {
        return productCount;
    }

    public void setProductCount(String productCount) {
        this.productCount = productCount;
    }

    public ArrayList<ProductResponse> getProductResponses() {
        return productResponses;
    }

    public void setProductResponses(ArrayList<ProductResponse> productResponses) {
        this.productResponses = productResponses;
    }
}
