package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class APIStockListResponse extends BaseResponse implements Serializable {
    @SerializedName("data")
    @Expose
    ArrayList<APIStockResponse> arrayList;

    public ArrayList<APIStockResponse> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<APIStockResponse> arrayList) {
        this.arrayList = arrayList;
    }
}
