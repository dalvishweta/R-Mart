package com.rmart.customer.shops.category.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Category implements Serializable {
    @SerializedName("parent_category_name")
    @Expose
    private String parentCategoryName;
    @SerializedName("category_ids")
    @Expose
    private String categoryIds;
    @SerializedName("parent_category_id")
    @Expose
    private int parentCategoryId;
    @SerializedName("parent_image")
    @Expose
    private String parentImage;
    @SerializedName("category_desc")
    @Expose
    private String categoryDesc;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("chield_check")
    @Expose
    private boolean chieldCheck;

    public String getParentCategoryName() {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName) {
        this.parentCategoryName = parentCategoryName;
    }

    public String getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds;
    }

    public int getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(int parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getParentImage() {
        return parentImage;
    }

    public void setParentImage(String parentImage) {
        this.parentImage = parentImage;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isChieldCheck() {
        return chieldCheck;
    }

    public void setChieldCheck(boolean chieldCheck) {
        this.chieldCheck = chieldCheck;
    }
}
