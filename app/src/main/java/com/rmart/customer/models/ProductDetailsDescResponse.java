package com.rmart.customer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.pojos.BaseResponse;

import java.io.Serializable;

/**
 * Created by Satya Seshu on 13/09/20.
 */
public class ProductDetailsDescResponse extends BaseResponse implements Serializable {

    @SerializedName("data")
    @Expose
    private ProductDetailsDescProductDataModel productDetailsDescProductDataModel;

    public ProductDetailsDescProductDataModel getProductDetailsDescProductDataModel() {
        return productDetailsDescProductDataModel;
    }

    public void setProductDetailsDescProductDataModel(ProductDetailsDescProductDataModel productDetailsDescProductDataModel) {
        this.productDetailsDescProductDataModel = productDetailsDescProductDataModel;
    }

    public static class ProductDetailsDescProductDataModel {

        @SerializedName("product_data")
        @Expose
        private ProductDetailsDescModel productDetailsDescModel;

        public ProductDetailsDescModel getProductDetailsDescModel() {
            return productDetailsDescModel;
        }

        public void setProductDetailsDescModel(ProductDetailsDescModel productDetailsDescModel) {
            this.productDetailsDescModel = productDetailsDescModel;
        }
    }
}
