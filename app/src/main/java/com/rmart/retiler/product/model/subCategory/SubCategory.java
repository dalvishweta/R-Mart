package com.rmart.retiler.product.model.subCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.retiler.product.model.Images;

import java.util.ArrayList;

public class SubCategory {
    @SerializedName("product_lib_id")
    @Expose
    int productLibId;
    @SerializedName("product_image")
    @Expose
    String productImage;
    @SerializedName("product_name")
    @Expose
    String productName;
    @SerializedName("product_desc")
    @Expose
    String productDesc;
    @SerializedName("category_id")
    @Expose
    int categoryId;
    @SerializedName("category_name")
    @Expose
    String categoryName;
    @SerializedName("sub_category_name")
    @Expose
    String subCategoryName;
    @SerializedName("brand_id")
    @Expose
    int brandId;
    @SerializedName("brand_name")
    @Expose
    String brandName;
    @SerializedName("images")
    @Expose
    ArrayList<Images> images;

    public int getProductLibId() {
        return productLibId;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public int getBrandId() {
        return brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public ArrayList<Images> getImages() {
        return images;
    }
}