package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.BaseResponse;

import java.util.ArrayList;

public class APICategoryListResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    ArrayList<APICategoryResponse> arrayList;

    public ArrayList<APICategoryResponse> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<APICategoryResponse> arrayList) {
        this.arrayList = arrayList;
    }
}
