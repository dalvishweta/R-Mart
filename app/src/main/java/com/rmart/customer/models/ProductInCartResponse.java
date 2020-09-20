package com.rmart.customer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.inventory.models.UnitObject;
import com.rmart.utilits.pojos.BaseResponse;
import com.rmart.utilits.pojos.ShowProductResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Satya Seshu on 13/09/20.
 */
public class ProductInCartResponse extends BaseResponse implements Serializable {

    @SerializedName("data")
    @Expose
    private ProductInCartDetailsDataModel productInCartDetailsDataModel;

    public ProductInCartDetailsDataModel getProductInCartDetailsDataModel() {
        return productInCartDetailsDataModel;
    }

    public void setProductInCartDetailsDataModel(ProductInCartDetailsDataModel productInCartDetailsDataModel) {
        this.productInCartDetailsDataModel = productInCartDetailsDataModel;
    }

    public static class ProductInCartDetailsDataModel {

        @SerializedName("cart_data")
        @Expose
        private List<ProductInCartDetailsModel> productInCartDetailsList = null;

        @SerializedName("total_cart_count")
        @Expose
        private Integer totalCartCount;
        @SerializedName("shop_data")
        @Expose
        private CustomerProductsShopDetailsModel vendorShopDetails;

        public List<ProductInCartDetailsModel> getProductInCartDetailsList() {
            return productInCartDetailsList;
        }

        public void setProductInCartDetailsList(List<ProductInCartDetailsModel> productInCartDetailsList) {
            this.productInCartDetailsList = productInCartDetailsList;
        }

        public Integer getTotalCartCount() {
            return totalCartCount;
        }

        public void setTotalCartCount(Integer totalCartCount) {
            this.totalCartCount = totalCartCount;
        }

        public CustomerProductsShopDetailsModel getVendorShopDetails() {
            return vendorShopDetails;
        }

        public void setVendorShopDetails(CustomerProductsShopDetailsModel vendorShopDetails) {
            this.vendorShopDetails = vendorShopDetails;
        }
    }
}
