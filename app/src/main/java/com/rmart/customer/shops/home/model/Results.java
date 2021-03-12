package com.rmart.customer.shops.home.model;

import com.google.gson.annotations.SerializedName;
import com.rmart.customer.dashboard.model.SliderImages;

import java.util.ArrayList;

public class Results {

    @SerializedName("type")
    public String type;
    @SerializedName("name")
    public String name;
    @SerializedName("viewmore")
    public String viewmore;
    @SerializedName("category")
    public ArrayList<Category> category;
    @SerializedName("product_data")
    public ArrayList<ProductData> productData;
    @SerializedName("banner")
    public ArrayList<SliderImages> banner;
    @SerializedName("Images")
    public ArrayList<AdvertiseImages> advertiseImages;


}
