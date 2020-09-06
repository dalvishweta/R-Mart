package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APIStockResponse extends BaseResponse {

    @SerializedName("stock_id")
    @Expose
    String stockID;

    @SerializedName("stock_name")
    @Expose
    String stockName;

    public String getStockID() {
        return stockID;
    }

    public void setStockID(String stockID) {
        this.stockID = stockID;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
}
