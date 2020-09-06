package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class APIBrandListResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    ArrayList<APIBrandResponse> arrayList;

    public ArrayList<APIBrandResponse> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<APIBrandResponse> arrayList) {
        this.arrayList = arrayList;
    }
}
