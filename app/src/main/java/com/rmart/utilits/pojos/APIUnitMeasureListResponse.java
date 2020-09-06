package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class APIUnitMeasureListResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    ArrayList<APIUnitMeasureResponse> arrayList;

    public ArrayList<APIUnitMeasureResponse> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<APIUnitMeasureResponse> arrayList) {
        this.arrayList = arrayList;
    }
}
