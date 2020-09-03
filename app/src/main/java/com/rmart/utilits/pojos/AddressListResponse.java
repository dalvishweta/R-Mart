package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AddressListResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    ArrayList<AddressResponse> response;

    public ArrayList<AddressResponse> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<AddressResponse> response) {
        this.response = response;
    }
}
