package com.rmart.utilits.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.BaseResponse;

import java.util.ArrayList;

public class DeliveryBoyList extends BaseResponse {
    @SerializedName("data")
    @Expose
    ArrayList<ProfileResponse> deliveryBoys;

    public ArrayList<ProfileResponse> getDeliveryBoys() {
        return deliveryBoys;
    }

    public void setDeliveryBoys(ArrayList<ProfileResponse> deliveryBoys) {
        this.deliveryBoys = deliveryBoys;
    }
}
