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
    private VendorProductDataResponse vendorProductDataResponse;

    public VendorProductDataResponse getVendorProductDataResponse() {
        return vendorProductDataResponse;
    }

    public void setVendorProductDataResponse(VendorProductDataResponse vendorProductDataResponse) {
        this.vendorProductDataResponse = vendorProductDataResponse;
    }

    public static class VendorProductDataResponse {
        @SerializedName("product_data")
        @Expose
        private List<CustomerProductDetailsModel> productsListData = null;
        @SerializedName("shop_data")
        @Expose
        private VendorProductShopDataResponse vendorShopDetails;

        public List<CustomerProductDetailsModel> getProductsListData() {
            return productsListData;
        }

        public void setProductsListData(List<CustomerProductDetailsModel> productsListData) {
            this.productsListData = productsListData;
        }

        public VendorProductShopDataResponse getVendorShopDetails() {
            return vendorShopDetails;
        }

        public void setVendorShopDetails(VendorProductShopDataResponse vendorShopDetails) {
            this.vendorShopDetails = vendorShopDetails;
        }
    }
}
