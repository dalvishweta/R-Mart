package com.rmart.retiler.product.model.product;

public class ProductListRequest {
    String startIndex;
    String endIndex;
    String mobile;
    String stockType;

    public ProductListRequest(String startIndex, String endIndex, String mobile, String stockType) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.mobile = mobile;
        this.stockType = stockType;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }
}
