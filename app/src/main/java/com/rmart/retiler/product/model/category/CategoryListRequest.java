package com.rmart.retiler.product.model.category;

public class CategoryListRequest {
    String categoryId;
    String startIndex;
    String endIndex;

    public CategoryListRequest(String categoryId, String startIndex, String endIndex) {
        this.categoryId = categoryId;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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
}
