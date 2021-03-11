package com.rmart.customer.shops.home.model;
import com.google.gson.annotations.SerializedName;
import com.rmart.customer.models.CustomerProductsDetailsUnitModel;
import com.rmart.utilits.BaseResponse;
import com.rmart.utilits.Utils;

import java.io.Serializable;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class ProductData extends BaseResponse  {

    @SerializedName("product_name")
    public String productName;
    @SerializedName("parent_category_id")
    public Integer parentCategoryId;
    @SerializedName("parent_category_name")
    public String parentCategoryName;
    @SerializedName("product_regional_name")
    public String product_regional_name;
    @SerializedName("stock_status_id")
    public int stock_status_id;
    @SerializedName("product_image")
    public String productImage;
    @SerializedName("product_id")
    public Integer productId;
    @SerializedName("product_expiry_date")
    public String productExpiryDate;
    @SerializedName("units")
    public List<CustomerProductsDetailsUnitModel> units = null;



    private boolean isHeader = false;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductExpiryDate() {
        return productExpiryDate;
    }

    public void setProductExpiryDate(String productExpiryDate) {
        this.productExpiryDate = productExpiryDate;
    }

    public List<CustomerProductsDetailsUnitModel> getUnits() {
        return units;
    }

    public void setUnits(List<CustomerProductsDetailsUnitModel> units) {
        this.units = units;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }


}
