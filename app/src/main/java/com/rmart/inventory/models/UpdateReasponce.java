package com.rmart.inventory.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.rmart.utilits.pojos.BaseResponse;

import java.io.Serializable;

public class UpdateReasponce  extends BaseResponse {

    @SerializedName("data")
    @Expose
    public UpdateProduct product;
}
