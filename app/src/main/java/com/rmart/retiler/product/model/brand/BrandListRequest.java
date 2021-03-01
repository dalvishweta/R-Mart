package com.rmart.retiler.product.model.brand;

public class BrandListRequest {
    String brandId;
    String startIndex;
    String endIndex;
    String venderID;

    public String getVenderID() {
        return venderID;
    }

    public void setVenderID(String venderID) {
        this.venderID = venderID;
    }

    public BrandListRequest(String brandId, String startIndex, String endIndex, String venderID) {
        this.brandId = brandId;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.venderID = venderID;
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
