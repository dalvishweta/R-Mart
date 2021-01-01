package com.rmart.profile.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ShopTypeResult{

    @SerializedName("shop_type")
    public ArrayList<ShopType> shopTypes;
    @SerializedName("business_type")
    public ArrayList<BusinessType> businessTypes;
}
