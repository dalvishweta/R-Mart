package com.rmart.customer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.pojos.BaseResponse;

import java.util.List;

/**
 * Created by Satya Seshu on 10/09/20.
 */
public class VendorProductDetailsResponse extends BaseResponse {

    @SerializedName("data")
    @Expose
    private CustomerProductDetailsModel customerProductDetailsModel;

    public CustomerProductDetailsModel getCustomerProductDetailsModel() {
        return customerProductDetailsModel;
    }

    public void setCustomerProductDetailsModel(CustomerProductDetailsModel customerProductDetailsModel) {
        this.customerProductDetailsModel = customerProductDetailsModel;
    }

    public static class CustomerProductDetailsModel {
        @SerializedName("product_data")
        @Expose
        private List<VendorProductDataResponse> productData = null;
        @SerializedName("shop_data")
        @Expose
        private VendorProductShopDataResponse shopData;

        public List<VendorProductDataResponse> getProductData() {
            return productData;
        }

        public void setProductData(List<VendorProductDataResponse> productData) {
            this.productData = productData;
        }

        public VendorProductShopDataResponse getShopData() {
            return shopData;
        }

        public void setShopData(VendorProductShopDataResponse shopData) {
            this.shopData = shopData;
        }
    }
}
