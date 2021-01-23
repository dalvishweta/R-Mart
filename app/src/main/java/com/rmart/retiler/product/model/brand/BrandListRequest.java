package com.rmart.retiler.product.model.brand;

public class BrandListRequest {
    String brandId;
    String startIndex;
    String endIndex;

    public BrandListRequest(String brandId, String startIndex, String endIndex) {
        this.brandId = brandId;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String categoryId) {
        this.brandId = categoryId;
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
