package com.rmart.retiler.product.model.subCategory;

public class SubCategoryListRequest {
    String startIndex;
    String endIndex;
    String subCategoryId;

    public SubCategoryListRequest(String startIndex, String endIndex, String subCategoryId) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.subCategoryId = subCategoryId;
    }

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

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }
}
