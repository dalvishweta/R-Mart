package com.rmart.retiler.inventory.product_from_inventory.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

        @SerializedName("product_id")
        @Expose
        private int productId;
        @SerializedName("product_cat_id")
        @Expose
        private int productCatId;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("product_details")
        @Expose
        private Object productDetails;
        @SerializedName("display_image")
        @Expose
        private String displayImage;
        @SerializedName("brand_id")
        @Expose
        private int brandId;
        @SerializedName("brand_name")
        @Expose
        private String brandName;
        @SerializedName("category_name")
        @Expose
        private String categoryName;
        @SerializedName("images")
        @Expose
        private List<productImage> images = null;

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getProductCatId() {
            return productCatId;
        }

        public void setProductCatId(int productCatId) {
            this.productCatId = productCatId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public Object getProductDetails() {
            return productDetails;
        }

        public void setProductDetails(Object productDetails) {
            this.productDetails = productDetails;
        }

        public String getDisplayImage() {
            return displayImage;
        }

        public void setDisplayImage(String displayImage) {
            this.displayImage = displayImage;
        }

        public int getBrandId() {
            return brandId;
        }

        public void setBrandId(int brandId) {
            this.brandId = brandId;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public List<productImage> getImages() {
            return images;
        }

        public void setImages(List<productImage> images) {
            this.images = images;
        }

    }

